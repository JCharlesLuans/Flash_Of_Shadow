/*
 * org.thunderbot.FOS.Serveur.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.serveur;

import org.thunderbot.FOS.database.FosDAO;
import org.thunderbot.FOS.database.HelperBD;
import org.thunderbot.FOS.database.beans.Joueur;
import org.thunderbot.FOS.serveur.networkObject.Authentification;

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
    }

    public void run() {
        traitement();
    }

    private void traitement() {
        System.out.println("Connection de : " + me.getInetAddress());

        try {
            ObjectOutputStream sortie = new ObjectOutputStream(me.getOutputStream());
            ObjectInputStream entree = new ObjectInputStream(me.getInputStream());

            connexion(entree, sortie);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void connexion(ObjectInputStream entree, ObjectOutputStream sortie) throws IOException, ClassNotFoundException {

        int code = 0;

        Joueur joueur;

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
            //si le joueur n'existe pas
            joueur.setPseudo(pseudo);
            joueur.setMdp(mdp);

            if (!joueur.isExistant()) {
                // Insertion en BD
                accesBD.addJoueur(joueur);
                code = 0;
            } else {
                code = 1;
            }

        } else {
            // Verification cohérance pseudo + mdp
            if (joueur.isExistant() && joueur.getMdp().equals(mdp)) {
                code = 0;
            } else {
                code = 2;
            }
        }

        sortie.writeObject(code);
        sortie.flush();

    }
}

