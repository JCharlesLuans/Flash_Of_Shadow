/*
 * authentification.java             27/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.serveur.networkObject;

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
    private boolean nouveauJoueur;

    public Authentification(String pseudo, String mdp, boolean nouveauJoueur) {
        this.pseudo = pseudo;
        this.mdp = mdp;
        this.nouveauJoueur = nouveauJoueur;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public boolean isNouveauJoueur() {
        return nouveauJoueur;
    }

    public void setNouveauJoueur(boolean nouveauJoueur) {
        this.nouveauJoueur = nouveauJoueur;
    }
}
