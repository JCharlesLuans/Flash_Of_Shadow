/*
 * org.thunderbot.FOS.Client.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Communique avec le serveur pour tenir a jour le client
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Client {

    public static final String SERVER_NAME = "localhost"; // Adresse du serveur
    public static final int SERVER_PORT = 6700;

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

        new Thread()
    }

    public void authentification(String pseudo) throws IOException {
        envoi(pseudo);
    }

    public void deconnexion() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    /**
     * @param object a envoyer
     */
    private void envoi(Object object) throws IOException {
        out.writeObject(object);
        out.flush();

        System.out.println("Client: donnees emises");
    }

    /**
     * Receptionne des donnée du serveurs
     * @return l'objets reçu
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private Object reception() throws IOException, ClassNotFoundException {
        Object objetRecu = in.readObject();
        return objetRecu;
    }
}
