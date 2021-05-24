/*
 * org.thunderbot.FOS.Serveur.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.serveur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * org.thunderbot.FOS.Serveur de FOS
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Serveur {

    /**
     * Lancement du serveur
     * @param args
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Lancement du serveur");
        ServeurJeu serveurJeu = new ServeurJeu();
        System.out.println("Socket créer");
        while (true) {
            System.out.println("En attente d'un nouveau clients");
            serveurJeu.accept();
        }
    }

}

