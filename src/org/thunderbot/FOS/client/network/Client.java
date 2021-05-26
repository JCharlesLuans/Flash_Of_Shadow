/*
 * org.thunderbot.FOS.Client.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.network;

import org.thunderbot.FOS.client.gameState.MapGameState;
import org.thunderbot.FOS.client.gameState.entite.Personnage;
import org.thunderbot.FOS.client.gameState.entite.ServPersonnage;
import org.thunderbot.FOS.utils.XMLTools;

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

    public static final String SERVER_NAME = "192.168.1.21"; // Adresse du serveur
    public static final int SERVER_PORT = 6700;

    private String pseudo;

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
        envoi("STOP");
        in.close();
        out.close();
        socket.close();
    }

    public void updateClient(MapGameState mapGameState) {
        new Thread(actualisationDonneeDistante(mapGameState.getListeJoueur())).start();
    }

    /**
     * @param string a envoyer
     */
    private void envoi(String string) throws IOException {
        out.writeObject(string);
        out.flush();
    }

    /**
     * Receptionne des donnée du serveurs
     * @return l'objets reçu
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private String reception() throws IOException {
        try {
            return (String) in.readObject();
        } catch (ClassNotFoundException err) {
            return "";
        }
    }

    private Runnable actualisationDonneeDistante(ArrayList<ServPersonnage> listeJoueur) {
        return () -> {

            // Joueur a actualiser existe ou pas
            boolean existe = false;

            while (true) {
                try {

                    String reception = reception();
                    ServPersonnage tmp = (ServPersonnage) XMLTools.decodeString(reception);

                    System.out.println("En attente de reception");
                    System.out.println(listeJoueur.size());

                    // Mise a jour des joueur qui existe
                    for (int i = 0; i < listeJoueur.size(); i++) {

                        System.out.println("HEAY");

                        if (listeJoueur.get(i).getPseudo().equals(tmp.getPseudo())) {

                            existe = true;

                            // Suppression de l'ancien puis remplacement
                            listeJoueur.remove(i);
                            listeJoueur.add(i, tmp);
                            System.out.println("Mise a jour");
                        }
                    }

                    // Ajout du joueur a la liste
                    if (!existe) {
                        listeJoueur.add(tmp);
                        System.out.println("Joueur inexistant");
                    }


                } catch(IOException err){
                    err.printStackTrace();
                }
            }
        };
    }

    public void updateServeur(MapGameState mapGameState) {
        try {
            ServPersonnage tmp = new ServPersonnage();
            tmp.setPositionX(mapGameState.getJoueur().getPositionX());
            tmp.setPositionY(mapGameState.getJoueur().getPositionY());
            tmp.setDirection(mapGameState.getJoueur().getDirection());
            tmp.setPseudo(mapGameState.getJoueur().getPseudo());

            envoi(XMLTools.encodeString(tmp));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
