/*
 * org.thunderbot.FOS.Client.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.network;

import org.thunderbot.FOS.client.gameState.MapGameState;
import org.thunderbot.FOS.client.gameState.entite.Personnage;
import org.thunderbot.FOS.client.gameState.entite.ServPersonnage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Communique avec le serveur pour tenir a jour le client
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Client {

    public static final String SERVER_NAME = "localhost"; // Adresse du serveur
    public static final int SERVER_PORT = 6700;

    private Socket socket;

    private ObjectOutputStream out; // Sortie du socket

    private ObjectInputStream in;   // Entrée du socket

    /**
     * Création de l'objet client, encapsulation de la socket
     */
    public Client() throws IOException {
        socket = new Socket(SERVER_NAME, SERVER_PORT);
        System.out.println("Socket client: " + socket);

        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();

        in = new ObjectInputStream(socket.getInputStream());
    }

    public void authentification(String pseudo) throws IOException {
        envoi(pseudo);
    }

    public void deconnexion() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    public void update(MapGameState mapGameState) {
        new Thread(actualisationDonneeSurServeur(mapGameState.getJoueur()));
        new Thread(actualisationDonneeDistante(mapGameState.getListeJoueur())).start();
    }

    /**
     * @param object a envoyer
     */
    private void envoi(Object object) throws IOException {
        out.writeObject(object);
        out.flush();

        System.out.println("Client: donnees emises");
    }

    /**
     * Receptionne des donnée du serveurs
     * @return l'objets reçu
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private Object reception() throws IOException, ClassNotFoundException {
        Object objetRecu = in.readObject();
        return objetRecu;
    }

    /**
     * Renvoie au serveur les nouvelle donée du client
     */
    private Runnable actualisationDonneeSurServeur(Personnage personnage) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    envoi(personnage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

    }

    private Runnable actualisationDonneeDistante(ArrayList<ServPersonnage> listeJoueur) {
        return new Runnable() {
            public void run() {

                System.out.println("En attente de reception");

                try {
                    ServPersonnage tmp = (ServPersonnage) reception();
                    for (int i = 0; i < listeJoueur.size(); i++) {

                        // Joueur a actualiser
                        if (listeJoueur.get(i).getPseudo().equals(tmp.getPseudo())) {

                            // Suppression de l'ancien puis remplacement
                            listeJoueur.remove(i);
                            listeJoueur.add(i, tmp);
                        }
                    }
                } catch (IOException | ClassNotFoundException err) {
                    err.printStackTrace();
                }
            }
        };
    }
}
