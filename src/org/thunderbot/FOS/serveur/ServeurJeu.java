/*
 * TraitementClient.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.serveur;

import org.thunderbot.FOS.client.gameState.entite.Personnage;
import org.thunderbot.FOS.client.network.Client;
import org.thunderbot.FOS.client.network.beans.Authentification;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.ArrayList;

/**
 * TODO decrire la classe
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class ServeurJeu  {

    public final static int PORT = 6700;

    ArrayList<ClientConnecter> listeClient;

    ServerSocket serverSocket;

    public ServeurJeu() throws IOException {
        listeClient = new ArrayList<>();
        serverSocket = new ServerSocket(PORT);
    }

    public void accept() {

        Socket socket = null;
        try {

            socket = this.serverSocket.accept();
            ClientConnecter clientConnecter = new ClientConnecter(socket);
            listeClient.add(clientConnecter);
            new Thread(runnableService(clientConnecter)).run();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    Runnable runnableService(final ClientConnecter client) {
        return new Runnable() {
            public void run() {

                try {

                    // Mise a jour des clients deja existant exepter le dernier
                    for (int i = 0; i < listeClient.size(); i++) {
                       if (!listeClient.get(i).equals(client)) {

                           // Envoi le personnage du nouveau client aux clients deja existant
                           listeClient.get(i).envoi(client.getPersonnage());

                           // Envoi des personnages des clients deja existant au nouveau clients
                           client.envoi(listeClient.get(i).getPersonnage());
                       }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        };
    }
}
