/*
 * Serveur.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * Serveur de FOS
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

        Socket soc = s.accept();

        System.out.println("Serveur a accepte connexion: " + soc);

        ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
        out.flush();

        ObjectInputStream in = new ObjectInputStream(soc.getInputStream());


        System.out.println("Serveur a cree les flux");

        int[] tableauAEmettre = {7, 8, 9};

        out.writeObject(tableauAEmettre);
        out.flush();

        System.out.println("Serveur: donnees emises");

        Object objetRecu = in.readObject();
        int[] tableauRecu = (int[]) objetRecu;

        System.out.println("Serveur recoit: " + Arrays.toString(tableauRecu));

        in.close();
        out.close();
        soc.close();
    }
}
