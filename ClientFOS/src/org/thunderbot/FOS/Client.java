/*
 * Client.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

/**
 * Communique avec le serveur pour tenir a jour le client
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Client {

    static final String serverName = "localhost";
    static final int serverPort = 6700;

    private Socket socket;

    private ObjectOutputStream out; // Sortie du socket

    private ObjectInputStream in;   // Entrée du socket

    /**
     * Création de l'objet client, encapsulation de la socket
     */
    public Client() throws IOException {
        socket = new Socket(serverName, serverPort);
        System.out.println("Socket client: " + socket);

        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();

        in = new ObjectInputStream(socket.getInputStream());

        System.out.println("Client a cree les flux");
    }

    public void deconnexion() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    public void envoi(Object object) throws IOException {
        out.writeObject(object);
        out.flush();

        System.out.println("Client: donnees emises");
    }

    public Object reception() throws IOException, ClassNotFoundException {
        Object objetRecu = in.readObject();
        return objetRecu;
    }
}
