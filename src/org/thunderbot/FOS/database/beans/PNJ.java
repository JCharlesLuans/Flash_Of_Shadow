package org.thunderbot.FOS.database.beans;

import org.thunderbot.FOS.client.gameState.entite.Personnage;
import org.thunderbot.FOS.client.gameState.entite.PersonnageNonJoueur;
import org.thunderbot.FOS.client.gameState.phisique.Stats;

import java.io.Serializable;

public class PNJ implements Serializable {
    private int id;
    private String nom;
    private String sprite;
    private int agressif;
    private float x;
    private float y;
    private Stats stats;
    private int idMap;
    private int idFaction;
    private int idTitre;

    private int direction;

    private Effet effet;

    public PNJ() {
        id = -1;
        nom = "";
        sprite = "";
        agressif = -1;
        x = -1f;
        y = -1f;
        stats = new Stats();
        direction = Personnage.HAUT;
        effet = new Effet();
    }

    public PNJ(int id, String nom, String sprite, int agressif, float x, float y, int statAgilite, int statArmure, int statDexterite, int statForce, int statEndurance, int statInteligence, int statSagesse, int idMap, int idFaction, int idTitre, Effet effet) {
        this.id = id;
        this.nom = nom;
        this.sprite = sprite;
        this.agressif = agressif;
        this.x = x;
        this.y = y;
        stats.setAgilite(statAgilite);
        stats.setArmure(statArmure);
        stats.setDexterite(statDexterite);
        stats.setEndurance(statEndurance);
        stats.setForce(statForce);
        stats.setIntelligence(statInteligence);
        stats.setSagesse(statSagesse);
        this.idMap = idMap;
        this.idFaction = idFaction;
        this.idTitre = idTitre;
    }

    public PNJ(PersonnageNonJoueur personnageNonJoueur) {
        this.id = personnageNonJoueur.getId();
        this.nom = personnageNonJoueur.getNom();
        this.sprite = personnageNonJoueur.getSprite();
        agressif = -1;

        x = personnageNonJoueur.getPositionX();
        y = personnageNonJoueur.getPositionY();

        stats = personnageNonJoueur.getStats();
        effet = personnageNonJoueur.getEffet();
        this.idMap = personnageNonJoueur.getIdMap();
        direction = personnageNonJoueur.getDirection();
    }

    public PNJ(PNJ pnj) {
        this.id              = pnj.id;
        this.nom             = pnj.nom;
        this.sprite          = pnj.sprite;
        this.agressif        = pnj.agressif;
        this.x               = pnj.x;
        this.y               = pnj.y;
        this.stats           = pnj.stats;
        this.effet           = pnj.effet;
        this.idMap           = pnj.idMap;
        this.idFaction       = pnj.idFaction;
        this.idTitre         = pnj.idTitre;
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

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public void setEffet(Effet effet) {
        this.effet = effet;
    }

    public Effet getEffet() {
        return effet;
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
                ", stats=" + stats +
                ", idMap=" + idMap +
                ", idFaction=" + idFaction +
                ", idTitre=" + idTitre +
                ", direction=" + direction +
                ", effet=" + effet +
                '}';
    }
}
