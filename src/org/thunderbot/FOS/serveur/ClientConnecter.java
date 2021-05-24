/*
 * ClientConnecter.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.serveur;

import org.thunderbot.FOS.client.gameState.entite.ServPersonnage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Client connecter au serveur. Definis par son pseudo
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class ClientConnecter {

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private ServPersonnage servPersonnage;

    private String pseudo; // ID

    /**
     * Nouveau client connecter
     * @param socket utiliser pour la connection
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ClientConnecter(Socket socket) throws IOException, ClassNotFoundException {
        /* Init entrée sortie */
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());

        // Init du clients
        this.pseudo = (String) reception();
        System.out.println("Connexion de : " + pseudo);
    }

    /**
     * Reception de donnée
     * @return l'objet reçu
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object reception() throws IOException, ClassNotFoundException {
        return in.readObject();
    }

    /**
     * Envoi de donnée
     * @param object a envoyer
     * @throws IOException
     */
    public void envoi(Object object) throws IOException {
        out.writeObject(object);
        out.flush();
    }

    /**
     * @return le personnage de ce client
     */
    public ServPersonnage getPersonnage() {
        return servPersonnage;
    }
}
