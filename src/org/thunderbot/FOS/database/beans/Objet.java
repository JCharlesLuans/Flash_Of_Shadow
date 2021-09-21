/*
 * Objet.java                 14/09/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.database.beans;

import java.io.Serializable;

/**
 * Représentation de la donnée Objet de la BD
 *
 * @author Jean-Charles Luans
 */
public class Objet implements Serializable {

    private int id;
    private String nom;
    private String desc;
    private int emplacement;
    private int equipableParClasse;
    private int statAgilite;
    private int statArmure;
    private int statDexterite;
    private int statEndurance;
    private int statForce;
    private int statIntelligence;
    private int statSagesse;
    private int dps;
    private String image;

    public Objet() {
        id = -1;
        nom = "";
        desc = "";
        emplacement = -1;
        equipableParClasse = -1;
        statAgilite = -1;
        statArmure = -1;
        statDexterite = -1;
        statEndurance = -1;
        statForce = -1;
        statIntelligence = -1;
        statSagesse = -1;
        dps = -1;
        image = "";
    }

    public Objet(int id, String nom, String desc, int emplacement, int equipableParClasse, int statAgilite, int statArmure, int statDexterite,int statEndurance, int statForce, int statIntelligence, int statSagesse, int dps, String image) {
        this.id = id;
        this.nom = nom;
        this.desc = desc;
        this.emplacement = emplacement;
        this.equipableParClasse = equipableParClasse;
        this.statAgilite = statAgilite;
        this.statArmure = statArmure;
        this.statDexterite = statDexterite;
        this.statEndurance = statEndurance;
        this.statForce = statForce;
        this.statIntelligence = statIntelligence;
        this.statSagesse = statSagesse;
        this.dps = dps;
        this.image = image;
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

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public int getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(int emplacement) {
        this.emplacement = emplacement;
    }

    public int getEquipableParClasse() {
        return this.equipableParClasse;
    }

    public void setEquipableParClasse(int equipableParClasse) {
        this.equipableParClasse = equipableParClasse;
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

    public int getStatDexterite() {
        return statDexterite;
    }

    public void setStatDexterite(int statDexterite) {
        this.statDexterite = statDexterite;
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

    public int getDps() {
        return dps;
    }

    public void setDps(int dps) {
        this.dps = dps;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Objet{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", desc='" + desc + '\'' +
                ", emplacement=" + emplacement +
                ", equipableParClasse=" + equipableParClasse +
                ", statAgilite=" + statAgilite +
                ", statArmure=" + statArmure +
                ", statDexterite=" + statDexterite +
                ", statEndurance=" + statEndurance +
                ", statForce=" + statForce +
                ", statIntelligence=" + statIntelligence +
                ", statSagesse=" + statSagesse +
                ", dps=" + dps +
                ", image='" + image + '\'' +
                '}';
    }
}
