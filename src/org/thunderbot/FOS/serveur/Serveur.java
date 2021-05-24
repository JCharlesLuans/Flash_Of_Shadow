/*
 * org.thunderbot.FOS.Serveur.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.serveur;

import org.thunderbot.FOS.client.network.beans.Authentification;

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

    public final static int PORT = 6700;

    /**
     * Lancement du serveur
     * @param args
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Lancement du serveur");

        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Socket serveur: " + s);

        while (true) {
            Socket soc = s.accept();

            System.out.println("org.thunderbot.FOS.Serveur a accepte connexion: " + soc);

            ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
            out.flush();

            ObjectInputStream in = new ObjectInputStream(soc.getInputStream());


            System.out.println("org.thunderbot.FOS.Serveur a cree les flux");

            int[] tableauAEmettre = {7, 8, 9};

            out.writeObject(tableauAEmettre);
            out.flush();

            System.out.println("org.thunderbot.FOS.Serveur: donnees emises");

            Authentification objetRecu = (Authentification) in.readObject();
            System.out.println("org.thunderbot.FOS.Serveur recoit: " + objetRecu);

        }
//        in.close();
//        out.close();
//        soc.close();
    }
}
