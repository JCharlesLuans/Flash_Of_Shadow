/*
 * org.thunderbot.FOS.Serveur.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.serveur;

import org.thunderbot.FOS.database.FosDAO;
import org.thunderbot.FOS.database.HelperBD;
import org.thunderbot.FOS.database.beans.Joueur;
import org.thunderbot.FOS.database.beans.Map;
import org.thunderbot.FOS.database.beans.Objet;
import org.thunderbot.FOS.database.beans.Personnage;
import org.thunderbot.FOS.serveur.networkObject.Authentification;
import org.thunderbot.FOS.serveur.networkObject.ChargementCarte;
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

                } else if (reception.getClass() == ChargementCarte.class) {
                    chargementCarte(sortie, (ChargementCarte) reception);
                } else if (reception.getClass() == Stop.class) {
                    // Gestion de la deconnection
                    // TODO ed
                    System.out.println("STOP");
                    deconnexion((Stop) reception);
                    isConnecter = false;
                    me.close();
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

    private void deconnexion(Stop stop) {
        Personnage personnage = stop.getPersonnage();
        accesBD.updatePersonnage(personnage.getId(), personnage);
        System.out.println("Deconnexion de : " + me.getInetAddress());
    }

    private void chargementCarte(ObjectOutputStream sortie, ChargementCarte chargementCarte) throws IOException {
        String nom;
        Map map;
        // Recupere le nom de la carte
        nom = chargementCarte.getCarte().getNom();

        // Recherche en BD la carte, ainsi que ses datas
        map = accesBD.getMapByName(nom);

        // Renvoi au client l'objet MAP
        sortie.writeObject(map);
        sortie.flush();
    }
}

