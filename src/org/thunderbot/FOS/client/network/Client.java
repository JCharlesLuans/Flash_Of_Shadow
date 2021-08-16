/*
 * org.thunderbot.FOS.Client.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.network;

import org.newdawn.slick.SlickException;
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
        DatagramPacket packetEnvoi = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(lectureIpServeur()), PORT_SERVEUR);
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

    /**
     * Reception et actualisation des donnée sur le client de jeu
     * @param listeJoueur liste des joueur a actualiser
     * @return un thread
     */
    private Runnable actualisationDonneeDistante(ArrayList<ServPersonnage> listeJoueur) {
        return () -> {


            // Joueur a actualiser existe ou pas
            boolean existe = false;

            while (true) {
                try {

                    String reception = reception();

                    Object objReception = XMLTools.decodeString(reception);

                    // Update
                    if (objReception.getClass() == Update.class) {
                        Update update = (Update) objReception;
                        ServPersonnage tmp = update.getServPersonnage();

                        // Mise a jour des joueur qui existe
                        for (int i = 0; i < listeJoueur.size(); i++) {

                            if (listeJoueur.get(i).getPseudo().equals(tmp.getPseudo())) {

                                existe = true;

                                // Mise à jours du joueur
                                listeJoueur.get(i).miseAJour(tmp);
                            }
                        }

                        // Ajout du nouveau joueur a la liste
                        if (!existe) {
                            listeJoueur.add(new ServPersonnage(tmp));
                        }

                    } else if (objReception.getClass() == Stop.class) {

                        // Deconnexion
                        Stop tmp = (Stop) objReception;

                        // Recherche du joueur a remove
                        for (int i = 0; i < listeJoueur.size(); i++) {

                            if (listeJoueur.get(i).getPseudo().equals(tmp.getPseudo())) {

                                // Suppression de l'ancien puis remplacement
                                listeJoueur.remove(i);
                                System.out.println("Deconexion de " + tmp.getPseudo());
                            }
                        }
                    }




                } catch(IOException | SlickException err){
                    err.printStackTrace();
                }
            }
        };
    }

    /**
     * Renvoi les donnée actuelle du client au serveur pour actualiser les autres clients
     * @param mapGameState - State mise a jour
     */
    public void updateServeur(MapGameState mapGameState) {
        try {
            envoi(XMLTools.encodeString(new Update(mapGameState.getJoueur())));
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

    private String lectureIpServeur() {
        return XMLTools.readXMLElement("option.xml", "ipServeur");
    }
}
