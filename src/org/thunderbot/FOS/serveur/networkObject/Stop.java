/*
 * Stop.java             27/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.serveur.networkObject;

/**
 * Classe de deconexion
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Stop {
    private String pseudo;

    public Stop() {
        pseudo = "";
    }

    public Stop(String newPseudo) {
        pseudo = newPseudo;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
