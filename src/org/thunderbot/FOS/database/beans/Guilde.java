/*
 * Guilde.java                 14/09/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.database.beans;

/**
 * TODO ecrire Java Doc
 *
 * @author Jean-Charles Luans
 */
public class Guilde {

    private int id;
    private String nom;

    public Guilde() {

    }

    public Guilde(int id, String nom) {
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
