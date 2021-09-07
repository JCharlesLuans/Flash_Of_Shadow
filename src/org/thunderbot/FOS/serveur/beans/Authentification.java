/*
 * authentification.java             27/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.serveur.beans;

import java.io.Serializable;

/**
 * Données d'authentification
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Authentification implements Serializable {
    private String pseudo;
    private String mdp;

    public Authentification() {
        pseudo = "";
    }

    public Authentification(String pseudo, String mdp) {
        this.pseudo = pseudo;
        this.mdp = mdp;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
