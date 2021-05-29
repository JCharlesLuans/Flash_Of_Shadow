/*
 * org.thunderbot.FOS.Client.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.network;

import org.thunderbot.FOS.client.gameState.MapGameState;
import org.thunderbot.FOS.client.gameState.entite.ServPersonnage;
import org.thunderbot.FOS.serveur.Serveur;
import org.thunderbot.FOS.serveur.beans.Authentification;
import org.thunderbot.FOS.serveur.beans.Stop;
import org.thunderbot.FOS.serveur.beans.Update;
import org.thunderbot.FOS.utils.XMLTools;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;

/**
 * Communique avec le serveur pour tenir a jour le client
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Client {

    public static final String SERVER_NAME = "192.168.1.21"; // Adresse du serveur
    public static final int PORT_SERVEUR = 6700;
    public static final int PORT_CLIENT = 6701;

    private String pseudo;

    private DatagramSocket socket;

    private ObjectOutputStream out; // Sortie du socket

    private ObjectInputStream in;   // Entrée du socket

    /**
     * Création de l'objet client, encapsulation de la socket
     */
    public Client() throws IOException {
        socket = new DatagramSocket(PORT_CLIENT);
        System.out.println("Socket client: " + socket);
    }

    /**
     * Authentification du client auprés du serveur
     * @param pseudo du client
     * @throws IOException
     */
    public void authentification(String pseudo) throws IOException {
        envoi(XMLTools.encodeString(new Authentification(pseudo)));
    }

    /**
     * Dconnecte le client auprés du serveur
     * @throws IOException
     */
    public void deconnexion() throws IOException {
        envoi(XMLTools.encodeString(new Stop(pseudo)));
    }

    /**
     * Actualise les données du client
     * @param mapGameState
     */
    public void updateClient(MapGameState mapGameState) {
        new Thread(actualisationDonneeDistante(mapGameState.getListeJoueur())).start();
    }

    /**
     * @param string a envoyer encoder en XML
     */
    private void envoi(String string) throws IOException {
        byte[] buffer = string.getBytes();
        DatagramPacket packetEnvoi = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(SERVER_NAME), PORT_SERVEUR);
        socket.send(packetEnvoi);
    }

    /**
     * Receptionne des donnée du serveurs
     * @return l'objets reçu
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private String reception() throws IOException {
        byte[] buffer = new byte[Serveur.TAILLE_BUFFER];
        DatagramPacket packetReception = new DatagramPacket(buffer, buffer.length);
        socket.receive(packetReception);
        return new String(packetReception.getData());
    }

    private Runnable actualisationDonneeDistante(ArrayList<ServPersonnage> listeJoueur) {
        return () -> {


            // Joueur a actualiser existe ou pas
            boolean existe = false;

            while (true) {
                System.out.println("lancement du thread");
                try {

                    System.out.println("En attente de reception");
                    String reception = reception();

                    Object objReception = XMLTools.decodeString(reception);

                    // Update
                    if (objReception.getClass() == Update.class) {
                        Update update = (Update) objReception;
                        ServPersonnage tmp = update.getServPersonnage();

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

                    } else if (objReception.getClass() == Stop.class) {

                        Stop tmp = (Stop) objReception;
                        // Deconnexion
                        // Recherche du joueur a remove
                        for (int i = 0; i < listeJoueur.size(); i++) {

                            if (listeJoueur.get(i).getPseudo().equals(tmp.getPseudo())) {

                                // Suppression de l'ancien puis remplacement
                                listeJoueur.remove(i);
                                System.out.println("Deconexion de " + tmp.getPseudo());
                            }
                        }
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

            envoi(XMLTools.encodeString(new Update(tmp)));
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
