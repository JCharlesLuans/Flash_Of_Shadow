/*
 * Faction.java                 14/09/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.database.beans;

import java.io.Serializable;

/**
 * TODO ecrire Java Doc
 *
 * @author Jean-Charles Luans
 */
public class Faction implements Serializable {

    private int id;
    private String nom;
    private Map mapStart;

    public Faction() {

    }

    public Faction(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.mapStart = null;
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

    public Map getMapStart() {
        return this.mapStart;
    }

    public void setMapStart(Map mapStart) {
        this.mapStart = mapStart;
    }
}
