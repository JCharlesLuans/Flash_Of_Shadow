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
        this.pseudo = reception();
        servPersonnage = new ServPersonnage();
        servPersonnage.setPseudo(pseudo);

        System.out.println("Connexion de : " + pseudo);
    }

    /**
     * Reception de donnée
     * @return l'objet reçu
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public String reception() throws IOException, ClassNotFoundException {
        return (String) in.readObject();
    }

    /**
     * Envoi de donnée
     * @param string a envoyer
     * @throws IOException
     */
    public void envoi(String string) throws IOException {
        out.writeObject(string);
        out.flush();

        out.reset(); // Evite de remplir le flux
    }

    /**
     * Reception de donnée
     * @return l'objet reçu
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public boolean actualiseClient() throws IOException, ClassNotFoundException {
        this.servPersonnage = (ServPersonnage) in.readObject();
        return servPersonnage != null;
    }

    /**
     * @return le personnage de ce client
     */
    public ServPersonnage getPersonnage() {
        return servPersonnage;
    }

    public void setPersonnage(ServPersonnage newServPersonnage) {
        this.servPersonnage = newServPersonnage;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public void setPseudo(String newPseudo) {
        this.pseudo = newPseudo;
    }

    public boolean stop() throws IOException, ClassNotFoundException {
        return reception().equals("STOP");
    }
}
