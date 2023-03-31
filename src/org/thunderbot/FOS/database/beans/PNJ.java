package org.thunderbot.FOS.database.beans;

import org.thunderbot.FOS.client.gameState.entite.Personnage;
import org.thunderbot.FOS.client.gameState.entite.PersonnageNonJoueur;

import java.io.Serializable;

public class PNJ implements Serializable {
    private int id;
    private String nom;
    private String sprite;
    private int agressif;
    private float x;
    private float y;
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

    private int direction;

    public PNJ() {
        id = -1;
        nom = "";
        sprite = "";
        agressif = -1;
        x = -1f;
        y = -1f;
        statAgilite = -1;
        statArmure = -1;
        statDexterite = -1;
        statEndurance = -1;
        statForce = -1;
        statInteligence = -1;
        statSagesse = -1;

        direction = Personnage.HAUT;
    }

    public PNJ(int id, String nom, String sprite, int agressif, float x, float y, int statAgilite, int statArmure, int statDexterite, int statForce, int statEndurance, int statInteligence, int statSagesse, int idMap, int idFaction, int idTitre) {
        this.id = id;
        this.nom = nom;
        this.sprite = sprite;
        this.agressif = agressif;
        this.x = x;
        this.y = y;
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

    public PNJ(PersonnageNonJoueur personnageNonJoueur) {
        this.id = personnageNonJoueur.getId();
        this.nom = personnageNonJoueur.getNom();
        this.sprite = personnageNonJoueur.getSprite();
        agressif = -1;
        x = -1f;
        y = -1f;
        statAgilite = -1;
        statArmure = -1;
        statDexterite = -1;
        statEndurance = -1;
        statForce = -1;
        statInteligence = -1;
        statSagesse = -1;

        direction = Personnage.HAUT;
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

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
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
                ", x=" + x +
                ", y=" + y +
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

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
}
