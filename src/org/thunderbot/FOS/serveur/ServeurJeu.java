/*
 * TraitementClient.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.serveur;

import org.thunderbot.FOS.client.gameState.entite.ServPersonnage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Serveur de jeu FOS
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

    /**
     * Accepte la connexion d'un nouveau socket
     * Et ajoute se dernier a la liste des clients connecter
     */
    public void accept() {

        try {

            listeClient.add(new ClientConnecter(serverSocket.accept()));
            new Thread(runnableService(listeClient.get(listeClient.size()-1))).start();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    /**
     * Thread qui geres les clients connecter, en transferant ses données au autre clients
     * @param client a gerer
     * @return le thread a run
     */
    Runnable runnableService(final ClientConnecter client) {
        return () -> {
                try {

                    while (client.reception() != null) {

                        // Mise a jour des clients deja existant
                        for (int i = 0; i < listeClient.size(); i++) {
                            if (!listeClient.get(i).equals(client)) {
                                // Envoi le personnage du nouveau client aux clients deja existant
                                listeClient.get(i).envoi(client.getPersonnage());

                                // Envoi des personnages des clients deja existant au nouveau clients
                                client.envoi(listeClient.get(i).getPersonnage());
                            }
                        }

                        // Blocage du thread pour attendre nouvelle reception
                        client.actualiseClient();

                        if (client.stop()) {
                            listeClient.remove(client);
                        }

                        System.out.println(client.getPersonnage().getDirection());
                    }

                    System.out.println("STOP : " + client.getPersonnage().getPseudo());

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
        };
    }
}
