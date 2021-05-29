/*
 * org.thunderbot.FOS.Serveur.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.serveur;

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
 * org.thunderbot.FOS.Serveur de FOS
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Serveur {

    public static final int TAILLE_BUFFER = 1024;

    public static final int PORT_SERVEUR = 6700;
    public static final int PORT_CLIENT = 6701;

    /**
     * Lancement du serveur
     * @param args
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Lancement du serveur");

        DatagramSocket socket = new DatagramSocket(PORT_SERVEUR);

        ArrayList<InetAddress> listeClientConnecter = new ArrayList<>();

        byte[] bufferReception = new byte[TAILLE_BUFFER];
        byte[] bufferEnvoi = new byte[TAILLE_BUFFER];

        String xmlRecu;


        while(true)
        {
            DatagramPacket recv = new DatagramPacket(bufferReception,bufferReception.length);
            DatagramPacket envoi = new DatagramPacket(bufferEnvoi, bufferEnvoi.length);

            // Reception et traitement de la donnée
            socket.receive(recv);
            xmlRecu = new String(recv.getData());
            Object donneeRecu = XMLTools.decodeString(xmlRecu);

            // Choix de l'action a effectuer
            if (donneeRecu.getClass() == Authentification.class) {

                // Authentification
                System.out.println("LOG : nouveau client");
                listeClientConnecter.add(recv.getAddress()); // Ajout du client

            } else if (donneeRecu.getClass() == Update.class) {

                // Update
                System.out.println("UPDATE");
                for (int i = 0; i < listeClientConnecter.size(); i++) {
                    if (!listeClientConnecter.get(i).equals(recv.getAddress())) {
                        System.out.println("Update envoyer a : " + listeClientConnecter.get(i));
                        envoi.setPort(PORT_CLIENT);
                        envoi.setAddress(listeClientConnecter.get(i));
                        envoi.setData(xmlRecu.getBytes());
                        socket.send(envoi);
                    }
                }

            } else if (donneeRecu.getClass() == Stop.class) {
                System.out.println("LOG : deconnexion");
                listeClientConnecter.remove(recv.getAddress()); // Suppression du client
            }
        }
    }
}

