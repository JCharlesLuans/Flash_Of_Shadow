/*
 * ClientConnecter.java                 17/09/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.serveur;

import org.thunderbot.FOS.database.FosDAO;
import org.thunderbot.FOS.database.beans.*;
import org.thunderbot.FOS.serveur.networkObject.Authentification;
import org.thunderbot.FOS.serveur.networkObject.RequeteServeur;
import org.thunderbot.FOS.serveur.networkObject.Update;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * TODO ecrire Java Doc
 *
 * @author Jean-Charles Luans
 */
public class ClientConnecter {

    private Socket socket;
    private Personnage personnage;

    private ObjectInputStream entree;
    private ObjectOutputStream sortie;

    /**
     * Thread
     */
    public ClientConnecter(Socket socket) throws IOException {
        this.socket = socket;
        sortie = new ObjectOutputStream(socket.getOutputStream());
        entree = new ObjectInputStream(socket.getInputStream());
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getSortie() {
        return sortie;
    }

    public ObjectInputStream getEntree() {
        return entree;
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    public void setPersonnage(Personnage personnage) {
        this.personnage = personnage;
    }
}
