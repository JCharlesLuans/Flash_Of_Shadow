package org.thunderbot.FOS.database.beans;

import java.io.Serializable;

public class PNJ implements Serializable {
    private int id;
    private String nom;
    private String sprite;
    private int agressif;
    private int statAgilite;
    private int statArmure;
    private int statDexterite;
    private int statForce;
    private int statEndurance;
    private int statInteligence;
    private int statSagesse;
    private int idMap;
    private int idFaction;
    private int idTitre;

    public PNJ() {
        id = -1;
        nom = "";
        sprite = "";
        agressif = -1;
        statAgilite = -1;
        statArmure = -1;
        statDexterite = -1;
        statEndurance = -1;
        statForce = -1;
        statInteligence = -1;
        statSagesse = -1;
    }

    public PNJ(int id, String nom, String sprite, int agressif, int statAgilite, int statArmure, int statDexterite, int statForce, int statEndurance, int statInteligence, int statSagesse, int idMap, int idFaction, int idTitre) {
        this.id = id;
        this.nom = nom;
        this.sprite = sprite;
        this.agressif = agressif;
        this.statAgilite = statAgilite;
        this.statArmure = statArmure;
        this.statDexterite = statDexterite;
        this.statForce = statForce;
        this.statEndurance = statEndurance;
        this.statInteligence = statInteligence;
        this.statSagesse = statSagesse;
        this.idMap = idMap;
        this.idFaction = idFaction;
        this.idTitre = idTitre;
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

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public int getAgressif() {
        return agressif;
    }

    public void setAgressif(int agressif) {
        this.agressif = agressif;
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

    public int getStatForce() {
        return statForce;
    }

    public void setStatForce(int statForce) {
        this.statForce = statForce;
    }

    public int getStatEndurance() {
        return statEndurance;
    }

    public void setStatEndurance(int statEndurance) {
        this.statEndurance = statEndurance;
    }

    public int getStatInteligence() {
        return statInteligence;
    }

    public void setStatInteligence(int statInteligence) {
        this.statInteligence = statInteligence;
    }

    public int getStatSagesse() {
        return statSagesse;
    }

    public void setStatSagesse(int statSagesse) {
        this.statSagesse = statSagesse;
    }

    public int getIdMap() {
        return idMap;
    }

    public void setIdMap(int idMap) {
        this.idMap = idMap;
    }

    public int getIdFaction() {
        return idFaction;
    }

    public void setIdFaction(int idFaction) {
        this.idFaction = idFaction;
    }

    public int getIdTitre() {
        return idTitre;
    }

    public void setIdTitre(int idTitre) {
        this.idTitre = idTitre;
    }

    @Override
    public String toString() {
        return "PNJ{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", sprite='" + sprite + '\'' +
                ", agressif=" + agressif +
                ", statAgilite=" + statAgilite +
                ", statArmure=" + statArmure +
                ", statDexterite=" + statDexterite +
                ", statForce=" + statForce +
                ", statEndurance=" + statEndurance +
                ", statInteligence=" + statInteligence +
                ", statSagesse=" + statSagesse +
                ", idMap=" + idMap +
                ", idFaction=" + idFaction +
                ", idTitre=" + idTitre +
                '}';
    }
}
