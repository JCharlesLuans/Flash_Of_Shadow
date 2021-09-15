/*
 * Titre.java                 14/09/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.database.beans;

import java.io.Serializable;

/**
 * TODO ecrire Java Doc
 *
 * @author Jean-Charles Luans
 */
public class Titre implements Serializable {
    private int id;
    private String nom;

    public Titre() {

    }

    public Titre(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
