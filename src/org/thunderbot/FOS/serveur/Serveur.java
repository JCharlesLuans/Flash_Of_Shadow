/*
 * org.thunderbot.FOS.Serveur.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.serveur;

import org.thunderbot.FOS.database.FosDAO;
import org.thunderbot.FOS.database.beans.*;
import org.thunderbot.FOS.serveur.networkObject.Authentification;
import org.thunderbot.FOS.serveur.networkObject.RequeteServeur;
import org.thunderbot.FOS.serveur.networkObject.Stop;
import org.thunderbot.FOS.serveur.networkObject.Update;
import org.thunderbot.FOS.utils.XMLTools;

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
            ServerSocket serverSocket = new ServerSocket(PORT);
            accesBD = new FosDAO();

            System.out.println("Lancement du serveur");

            while (true) {
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
        System.out.println("Connection de : " + me.getSocket().getInetAddress());

        try {
            sortie = me.getSortie();
            entree = me.getEntree();

            while (!isIdentifier) {

                // TODO ED
                System.out.println(me.getSocket().getInetAddress() + " n'est pas identifier");
                connexion(entree, sortie);
            }

            // TODO ED
            System.out.println(me.getSocket().getInetAddress() + " est identifier");

            while (isConnecter) {
                Object reception = entree.readObject();

                if (reception.getClass() == Update.class) {
                    // Gestion de l'updae

                    // TODO ed
                    System.out.println("UPDATE");

                } else if (reception.getClass() == RequeteServeur.class) {
                    traitementRequeteServeur(entree, sortie, (RequeteServeur) reception);
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

            // TODO debug
            System.out.println("Nouveau joueur");

            //si le joueur n'existe pas
            joueur.setPseudo(pseudo);
            joueur.setMdp(mdp);

            if (!joueur.isExistant()) {
                // Insertion en BD
                accesBD.addJoueur(joueur);
                joueur = accesBD.getJoueurByPseudo(joueur.getPseudo());
                code = 0;
                isIdentifier = true;

                // TODO debug
                System.out.println("Nouveau joueur créer");

            } else {
                code = 1;

                // TODO debug
                System.out.println("Joueur existant");
            }

        } else {
            // Verification cohérance pseudo + mdp
            if (joueur.isExistant() && joueur.getMdp().equals(mdp)) {

                // TODO debug
                System.out.println("Joueur authentifier : " + joueur.getId());

                code = 0;
                isIdentifier = true;

                // recherche du personnage de ce joueur
                personnage = accesBD.getPersonnageByJoueur(joueur);

                me.setPersonnage(personnage);
                me.setConnecter(true);

            } else {

                // TODO debug
                System.out.println("Echec auth car non trouver");

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
    private void deconnexion(ObjectInputStream entree, ObjectOutputStream sortie) {

        Personnage personnageAReconstruire = new Personnage();


        try {

            personnageAReconstruire = receptionPersonnage();

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

    private void traitementRequeteServeur(ObjectInputStream entree, ObjectOutputStream sortie, RequeteServeur requeteServeur) throws IOException, ClassNotFoundException {
        switch (requeteServeur.getMotif()) {

            // Gere les demande de chargement d'objet du jeu, stocker en BD
            case RequeteServeur.CHARGEMENT:
                me.setConnecter(false);
                switch (requeteServeur.getObjet()) {
                    case RequeteServeur.MAP:
                        chargementCarte(sortie, requeteServeur.getCle());
                        break;
                    case RequeteServeur.CLASSE:
                        chargementClasse(sortie, requeteServeur.getCle());
                        break;
                    case RequeteServeur.FACTION:
                        chargementFaction(sortie, requeteServeur.getCle());
                        break;
                    case RequeteServeur.STUFF_BASE:
                        chargementStuffBase(sortie);
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

                        //TODO ED
                        System.out.println(personnage);
                        me.setPersonnage(personnage);
                }
                break;

            // Gere les mise a jour des clients
            case RequeteServeur.UPDATE:
                switch (requeteServeur.getObjet()) {
                    case RequeteServeur.MOUVEMENT:
                        updateMouvement();
                }
                break;

            // Gere la deconnexion
            case RequeteServeur.DECONNEXION:
                deconnexion(entree, sortie);
                break;
        }
    }

    private void chargementStuffBase(ObjectOutputStream sortie) {
        ArrayList<Objet> aRetourner;
        aRetourner = accesBD.getListeStuffBase();
        try {
            sortie.writeObject(aRetourner);
            sortie.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chargementCarte(ObjectOutputStream sortie, String nom) throws IOException {
        Map map;

        // Recherche en BD la carte, ainsi que ses datas
        map = accesBD.getMapByName(nom);

        // Renvoi au client l'objet MAP
        sortie.writeObject(map);
        sortie.flush();
    }

    private void chargementClasse(ObjectOutputStream sortie, String nom) throws IOException {
        Classe classe = accesBD.getClasseByName(nom);
        sortie.writeObject(classe);
        sortie.flush();
    }

    private void chargementFaction(ObjectOutputStream sortie, String nom) throws IOException {
        Faction faction = accesBD.getFactionByName(nom);
        sortie.writeObject(faction);
        sortie.flush();
    }

    private void updateMouvement() {

        try {
            me.setPersonnage(receptionPersonnage());
            envoiXML(listePersonnageConnecter(me.getPersonnage().getMap().getId()));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

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

    public void envoiXML(Object object) {
        try {
            String strEnvoi;
            strEnvoi = XMLTools.encodeString(object);
            sortie.writeObject(strEnvoi);
            sortie.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

