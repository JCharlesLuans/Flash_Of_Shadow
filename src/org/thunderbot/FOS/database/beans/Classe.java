/*
 * Classe.java                      14/09/2021
 * Copyright et copyleft TNLag Corp.
 */
package org.thunderbot.FOS.database.beans;

import java.io.Serializable;

public class Classe implements Serializable {

    private int id;
    private String nom;
    private int statAgilite;
    private int statArmure;
    private int statEndurance;
    private int statForce;
    private int statIntelligence;
    private int statSagesse;

    public Classe() {
        id = -1;
        nom = "";
        statAgilite = -1;
        statArmure = -1;
        statEndurance = -1;
        statForce = -1;
        statIntelligence = -1;
        statSagesse = -1;
    }

    public Classe(int id, String nom, int statAgilite, int statArmure, int statEndurance, int statForce, int statIntelligence, int statSagesse) {
        this.id = id;
        this.nom = nom;
        this.statAgilite = statAgilite;
        this.statArmure = statArmure;
        this.statEndurance = statEndurance;
        this.statForce = statForce;
        this.statIntelligence = statIntelligence;
        this.statSagesse = statSagesse;
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

    public int getStatAgilite() {
        return statAgilite;
    }

    public void setStatAgilite(int statAgilite) {
        this.statAgilite = statAgilite;
    }

    public int getStatArmure() {
        return statArmure;
    }

    public void setStatArmure(int statArmure) {
        this.statArmure = statArmure;
    }

    public int getStatEndurance() {
        return statEndurance;
    }

    public void setStatEndurance(int statEndurance) {
        this.statEndurance = statEndurance;
    }

    public int getStatForce() {
        return statForce;
    }

    public void setStatForce(int statForce) {
        this.statForce = statForce;
    }

    public int getStatIntelligence() {
        return statIntelligence;
    }

    public void setStatIntelligence(int statIntelligence) {
        this.statIntelligence = statIntelligence;
    }

    public int getStatSagesse() {
        return statSagesse;
    }

    public void setStatSagesse(int statSagesse) {
        this.statSagesse = statSagesse;
    }

    @Override
    public String toString() {
        return "Classe{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", statAgilite=" + statAgilite +
                ", statArmure=" + statArmure +
                ", statEndurance=" + statEndurance +
                ", statForce=" + statForce +
                ", statIntelligence=" + statIntelligence +
                ", statSagesse=" + statSagesse +
                '}';
    }
}
