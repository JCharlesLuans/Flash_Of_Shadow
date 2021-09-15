/*
 * Map.java                 14/09/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.database.beans;

import java.io.Serializable;

/**
 * Représentation de la donnée Map de la BD
 *
 * @author Jean-Charles Luans
 */
public class Map implements Serializable {

    private int id;
    private String nom;
    private int niveauPNJ;
    private int nombreMob;

    public Map() {
        id = -1;
        nom = "";
        niveauPNJ = -1;
        nombreMob = -1;
    }

    public Map(int id, String nom, int niveauPNJ, int nombreMob) {
        this.id = id;
        this.nom = nom;
        this.niveauPNJ = niveauPNJ;
        this.nombreMob = nombreMob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNiveauPNJ() {
        return niveauPNJ;
    }

    public void setNiveauPNJ(int niveauPNJ) {
        this.niveauPNJ = niveauPNJ;
    }

    public int getNombreMob() {
        return nombreMob;
    }

    public void setNombreMob(int nombreMob) {
        this.nombreMob = nombreMob;
    }

    @Override
    public String toString() {
        return "Map{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", niveauPNJ=" + niveauPNJ +
                ", nombreMob=" + nombreMob +
                '}';
    }
}
