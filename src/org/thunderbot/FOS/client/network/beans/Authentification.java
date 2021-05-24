/*
 * Autentification.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.network.beans;

import java.io.Serializable;

/**
 * Envoie de l'objet Authentification pour la réaliser aupres du serveur
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Authentification implements Serializable {

    String pseudo;

    public Authentification(String pseudo) {
        this.pseudo = pseudo;
    }
}
