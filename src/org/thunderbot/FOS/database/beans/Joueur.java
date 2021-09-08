/* Joueur.java             08/09/2021
 * Copyright et copyleft ThunderBot
 */
package org.thunderbot.FOS.database.beans;

public class Joueur {

    String pseudo;
    String mdp;
    int id;

    boolean existant;

    public Joueur() {
        this.pseudo = "";
        this.mdp = "";
        id = -1;
        existant = false;
    }

    public Joueur(String pseudo, String mdp, int id, boolean existant) {
        this.pseudo = pseudo;
        this.mdp = mdp;
        this.id = id;
        this.existant = existant;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isExistant() {
        return existant;
    }

    public void setExistant(boolean existant) {
        this.existant = existant;
    }
}
