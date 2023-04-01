/*
 * org.thunderbot.FOS.Serveur.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.serveur;

import org.thunderbot.FOS.database.FosDAO;
import org.thunderbot.FOS.database.beans.*;
import org.thunderbot.FOS.serveur.networkObject.Authentification;
import org.thunderbot.FOS.serveur.networkObject.RequeteServeur;
import org.thunderbot.FOS.utils.Tools;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * org.thunderbot.FOS.Serveur de FOS
 *
 * @author J-Charles Luans
 * @version 2.0
 */
public class Serveur extends Thread {

    public static final int PORT = 6700;

    private ArrayList<ClientConnecter> listeSocketClient;
    private ClientConnecter me;

    private static IAServeur iaServeur;

    private static FosDAO accesBD;

    private boolean isIdentifier;
    private boolean isConnecter;

    private ObjectInputStream entree;
    private ObjectOutputStream sortie;

    /**
     * Lancement du serveur
     * @param args
     */
    public static void main(String[] args) {

        ArrayList<ClientConnecter> listeClientConnecter = new ArrayList<>(); // Liste des sockets des clients connectés

        try {
            ServerSocket serverSocket = new ServerSocket(PORT); // Socket du serveur
            accesBD = new FosDAO();                             // Creation de l'acces a la BD
            CmdServeur cmdServeur = new CmdServeur(accesBD);    // Creation de l'objet serveur permettant d'executer des cmd
            cmdServeur.start();

            iaServeur = new IAServeur(accesBD);
            iaServeur.start();


            while (cmdServeur.serveurOn) {
                Socket newClient = serverSocket.accept();
                ClientConnecter clientConnecter = new ClientConnecter(newClient);
                listeClientConnecter.add(clientConnecter);
                Serveur thread = new Serveur(listeClientConnecter, clientConnecter);
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Thread
     */
    public Serveur(ArrayList<ClientConnecter> listeSocketClient, ClientConnecter me) {
        this.listeSocketClient = listeSocketClient;
        this.me = me;
        isConnecter = true;
    }

    public void run() {
        traitement();
    }

    private void traitement() {
        System.out.println("Connexion de : " + me.getSocket().getInetAddress());

        // DEBUG LOG
        System.out.println(iaServeur.getPnjByMap(1));

        try {
            sortie = me.getSortie();
            entree = me.getEntree();

            while (!isIdentifier) {
                connexion(entree, sortie);
            }

            // LOG
            System.out.println(me.getSocket().getInetAddress() + " est identifier");

            while (isConnecter) {
                Object reception = entree.readObject();

                if (reception.getClass() == RequeteServeur.class) {
                    traitementRequeteServeur((RequeteServeur) reception);
                }

            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }



    /**
     * Permet d'authentifier un joueur et de le connecter au serveur, permet
     * de lui envoyer les donnée relative a son personnage
     * @param entree
     * @param sortie
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void connexion(ObjectInputStream entree, ObjectOutputStream sortie) throws IOException, ClassNotFoundException {

        // TODO ECHAPER CHAR SPECIAUX SQL

        int code = 0;

        Joueur joueur;
        Personnage personnage = new Personnage();

        String pseudo;
        String mdp;
        boolean nouveauJoueur;

        Object reception = entree.readObject();
        Authentification tmp = (Authentification) reception;

        pseudo = tmp.getPseudo();
        mdp    = tmp.getMdp();
        nouveauJoueur = tmp.isNouveauJoueur();

        joueur = accesBD.getJoueurByPseudo(pseudo);

        if (nouveauJoueur) {

            me.setConnecter(false);

            //si le joueur n'existe pas
            joueur.setPseudo(pseudo);
            joueur.setMdp(mdp);

            if (!joueur.isExistant()) {
                // Insertion en BD
                accesBD.addJoueur(joueur);
                joueur = accesBD.getJoueurByPseudo(joueur.getPseudo());
                code = 0;
                isIdentifier = true;
            } else {
                code = 1;
            }

        } else {
            // Verification cohérance pseudo + mdp
            if (joueur.isExistant() && joueur.getMdp().equals(mdp)) {
                code = 0;
                isIdentifier = true;

                // recherche du personnage de ce joueur
                personnage = accesBD.getPersonnageByJoueur(joueur);
                me.setPersonnage(personnage);
                me.setConnecter(true);
            } else {
                code = 2;
            }
        }

        sortie.writeObject(code);
        sortie.flush();

        if (code == 0 && !nouveauJoueur) {
            sortie.writeObject(personnage);
            sortie.flush();
        } else if (code == 0 && nouveauJoueur) {
            // Attente du nouveau joueur
            sortie.writeObject(joueur);
            sortie.flush();
        }

    }

    /**
     * Permet de deconnecter un joueur et son client de maniere propre, puis dde sauvegarder ses donnée en DB
     */
    private void deconnexion() {

        Personnage personnageAReconstruire = new Personnage();


        try {

            personnageAReconstruire = receptionPersonnage();
            me.clientClose();
            me.getSocket().close();
            listeSocketClient.remove(me);
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Sauvegarde
        if (personnageAReconstruire.getId() == -1) {
            // Suppression du joeueur car il n'a pas créer son personnage
            accesBD.deleteJoueur(personnageAReconstruire.getIdJoueur());
        } else {
            accesBD.updatePersonnage(personnageAReconstruire.getId(), personnageAReconstruire);
        }

        System.out.println("Deconnexion de : " + me.getSocket().getInetAddress());

        isConnecter = false;

    }

    private void traitementRequeteServeur(RequeteServeur requeteServeur) throws IOException, ClassNotFoundException {

        switch (requeteServeur.getMotif()) {

            // Gere les demande de chargement d'objet du jeu, stocker en BD
            case RequeteServeur.CHARGEMENT:
                me.setConnecter(false);
                switch (requeteServeur.getObjet()) {
                    case RequeteServeur.MAP:
                        chargementCarte(requeteServeur.getCle());
                        break;
                    case RequeteServeur.CLASSE:
                        chargementClasse(requeteServeur.getCle());
                        break;
                    case RequeteServeur.FACTION:
                        chargementFaction(sortie, requeteServeur.getCle());
                        break;
                    case RequeteServeur.STUFF_BASE:
                        chargementStuffBase();
                        break;
                    case RequeteServeur.PNJ:
                        chargementPNJ(requeteServeur.getCle());
                        break;
                }
                me.setConnecter(true);
                break;

            // Gere les demande de création d'objet du jeu, stocker en BD
            case  RequeteServeur.CREATE:
                switch (requeteServeur.getObjet()) {
                    case RequeteServeur.PERSONNAGE:
                        Personnage personnage = (Personnage) entree.readObject();
                        sortie.writeObject(accesBD.addPersonnage(personnage));
                        sortie.flush();
                        
                        me.setPersonnage(personnage);
                }
                break;

            // Gere les mise a jour des clients
            case RequeteServeur.UPDATE:
                switch (requeteServeur.getObjet()) {
                    case RequeteServeur.MOUVEMENT:
                        updateMouvementJoueurs();
                        break;

                    case RequeteServeur.PNJ:
                        updateMouvementPNJ();
                        break;

                    case RequeteServeur.COMBAT:
                        lancementCombat();
                }
                break;

            // Gere la deconnexion
            case RequeteServeur.DECONNEXION:
                deconnexion();
                break;
        }
    }


    private void chargementPNJ(String resteRequete) {

        System.out.println(resteRequete);

        ArrayList<PNJ> aRenvoyer = new ArrayList<>();
        String table = resteRequete.split(":")[0];
        String id = resteRequete.split(":")[1];

        switch (table) {
            case "Map":
                aRenvoyer = accesBD.getPnjByIdMap(id);
                break;
        }

        System.out.println(aRenvoyer);
        envoiXML(aRenvoyer);
    }

    private void chargementStuffBase() {
        for (int i = 2; i < 8; i++) {
            envoiXML(accesBD.getObjetById(i));
        }
    }

    private void chargementCarte(String nom) throws IOException {
        Map map;

        // Recherche en BD la carte, ainsi que ses datas
        map = accesBD.getMapByName(nom);

        // Renvoi au client l'objet MAP
        sortie.writeObject(map);
        sortie.flush();
    }

    private void chargementClasse(String nom) throws IOException {
        Classe classe = accesBD.getClasseByName(nom);
        sortie.writeObject(classe);
        sortie.flush();
    }

    private void chargementFaction(ObjectOutputStream sortie, String nom) throws IOException {
        Faction faction = accesBD.getFactionByName(nom);
        sortie.writeObject(faction);
        sortie.flush();
    }

    private void updateMouvementJoueurs() {
        me.setPersonnage((Personnage) receptionXML());

        // Renvoi la liste des joueurs, ainsi que leurs position actuelles
        envoiXML(listePersonnageConnecter(me.getPersonnage().getMap().getId()));
    }

    private void updateMouvementPNJ() {
        envoiXML(iaServeur.getPnjByMap(me.getPersonnage().getMap().getId()));
    }

    /**
     * Renvoi la liste des personnages joueur a afficher et mettre à sur un client distant
     * @param idMap l'id de la map sur laquelle se trouve le joueur
     * @return la liste
     */
    private ArrayList<Personnage> listePersonnageConnecter(int idMap) {
        ArrayList<Personnage> aRetourner = new ArrayList<>();


        for (int i = 0; i < listeSocketClient.size(); i++) {
            if (listeSocketClient.get(i).isConnecter() &&
                    !listeSocketClient.get(i).equals(me) &&
                    listeSocketClient.get(i).getPersonnage().getMap().getId() == idMap
                    ) {
                aRetourner.add(listeSocketClient.get(i).getPersonnage());
            }
        }
        return aRetourner;
    }

    private Personnage receptionPersonnage() throws IOException, ClassNotFoundException {
        // Objet permettant de reconstruire le personnage a enregistrer
        int id = -1;
        float x = -1f;
        float y = -1f;
        Map tmpMap = null;
        Personnage personnageAReconstruire = null;

        // Reception des valeurs pour la reconstruction
        id = (int) entree.readObject();
        x = (float) entree.readObject();
        y = (float) entree.readObject();
        tmpMap = (Map) entree.readObject();
        personnageAReconstruire = (Personnage) entree.readObject();

        // Reconstruction de l'objet personnage à enregistrer
        personnageAReconstruire.setId(id);
        personnageAReconstruire.setX(x);
        personnageAReconstruire.setY(y);
        personnageAReconstruire.setMap(tmpMap);

        return personnageAReconstruire;
    }


    /**
     * Envoi d'un String contenant un objet serialiser en XML. L'objet va etre serialiser puis envoyer
     * @param object a sérialiser puis a envoyer
     */
    public void envoiXML(Object object) {
        try {
            String strEnvoi;
            strEnvoi = Tools.encodeString(object);
            sortie.writeObject(strEnvoi);
            sortie.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reception d'un String contenant un objet serialiser en XML. L'objet va etre desserialiser puis retourner
     * @return l'objet déserialiser
     */
    private Object receptionXML() {
        String strReception = null;
        try {
            strReception = (String) entree.readObject();
            return Tools.decodeString(strReception);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Tools.decodeString(strReception);
    }

    /**
     * Lance un combat entre le joueur et le PNJ
     */
    private void lancementCombat() {

        ArrayList<PNJ> listePnjPossible;
        ArrayList<PNJ> listePnjChoisis = new ArrayList<>();

        PNJ pnjTemporaire;
        Personnage personnageTemporaire;

        int nombrePNJ = 0;

        // LOG
        System.out.println("Entrée en combat");

        // Attente de la reception des infos complementaire => PNJ
        pnjTemporaire = (PNJ) receptionXML();
        // Attente de la reception des infos complementaire => Joueur
        personnageTemporaire = (Personnage) receptionXML();

        // Génération du nombre de PNJ que va affronter le joueur (entre 2 et 4)
        nombrePNJ = 2 + (int) (Math.random() * (4 - 2) + 1);

        // Recuperation des PNJ possibles sur la maps
        listePnjPossible = accesBD.getPnjByIdMap(pnjTemporaire.getIdMap() + "");

        // Pour tout le nombres de PNJ possible, choisir aléatoirement les pnj dans la liste créer précédament
        for (int i = 0; i < nombrePNJ; i++) {
            // TODO selectionnée seulement ceux qui sont agressif
            listePnjChoisis.add(listePnjPossible.get((int) (Math.random() * (listePnjPossible.size()) )));
        }

        // Renvoi de la liste au joueur
        envoiXML(listePnjChoisis);

    }
}

