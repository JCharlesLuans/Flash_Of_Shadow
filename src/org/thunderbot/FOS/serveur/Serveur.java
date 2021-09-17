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

    private ArrayList<Socket> listeSocketClient;
    private Socket me;
    private static FosDAO accesBD;

    private boolean isIdentifier;
    private boolean isConnecter;

    /**
     * Lancement du serveur
     * @param args
     */
    public static void main(String[] args) {

        ArrayList<Socket> listeSocketClient = new ArrayList<>(); // Liste des sockets des clients connectés

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            accesBD = new FosDAO();

            System.out.println("Lancement du serveur");

            while (true) {
                Socket newClient = serverSocket.accept();
                listeSocketClient.add(newClient);
                Serveur thread = new Serveur(listeSocketClient, newClient);
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Thread
     */
    public Serveur(ArrayList<Socket> listeSocketClient, Socket me) {
        this.listeSocketClient = listeSocketClient;
        this.me = me;
        isConnecter = true;
    }

    public void run() {
        traitement();
    }

    private void traitement() {
        System.out.println("Connection de : " + me.getInetAddress());

        try {
            ObjectOutputStream sortie = new ObjectOutputStream(me.getOutputStream());
            ObjectInputStream entree = new ObjectInputStream(me.getInputStream());

            while (!isIdentifier) {

                // TODO ED
                System.out.println(me.getInetAddress() + " n'est pas identifier");
                connexion(entree, sortie);
            }

            // TODO ED
            System.out.println(me.getInetAddress() + " est identifier");

            while (isConnecter) {
                Object reception = entree.readObject();

                if (reception.getClass() == Update.class) {
                    // Gestion de l'updae

                    // TODO ed
                    System.out.println("UPDATE");

                } else if (reception.getClass() == Stop.class) {
                    // Gestion de la deconnection
                    // TODO ed
                    System.out.println("STOP");
                    deconnexion((Stop) reception);
                    isConnecter = false;
                    me.close();
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

                // ATTENTE DU PERSONNAGE CREER
                // entree.readObject();

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

                // recherche du personnage de se joueur
                personnage = accesBD.getPersonnageByJoueur(joueur);

                // TODO ed
                System.out.println(personnage.toString());

            } else {

                // TODO debug
                System.out.println("Echec auth car non trouver");

                code = 2;
            }
        }

        // TODO DUBUG
        // System.out.println(code);

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
     * @param stop
     */
    private void deconnexion(Stop stop) {
        Personnage personnage = stop.getPersonnage();
        accesBD.updatePersonnage(personnage.getId(), personnage);
        System.out.println("Deconnexion de : " + me.getInetAddress());
    }

    private void traitementRequeteServeur(ObjectInputStream entree, ObjectOutputStream sortie, RequeteServeur requeteServeur) throws IOException {
        switch (requeteServeur.getMotif()) {
            case RequeteServeur.CHARGEMENT:
                switch (requeteServeur.getObjet()) {
                    case RequeteServeur.MAP:
                        chargementCarte(sortie, requeteServeur.getCle());
                        break;
                    case RequeteServeur.CLASSE:
                        chargementClasse(sortie, requeteServeur.getCle());
                        break;
                    case RequeteServeur.FACTION:
                        chargementFaction(sortie, requeteServeur.getCle());
                }
            break;
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
}

