/*
 * Personnage.java                      14/09/2021
 * Copyright et copyleft TNLag Corp.
 */
package org.thunderbot.FOS.database.beans;

import java.io.Serializable;

public class Personnage implements Serializable {

    // Attribu de l'objet en BD
    private int id;
    private String nom;
    private String sprite;
    private float x;
    private float y;
    private int idJoueur;
    private Classe classe;
    private Map map;
    private Objet stuffTete;
    private Objet stuffTorse;
    private Objet stuffGant;
    private Objet stuffJambe;
    private Objet stuffBotte;
    private Objet stuffArme;
    private Faction faction;
    private Guilde guilde;
    private Titre titre;

    //Autre
    private int direction;
    private boolean moving;

    public Personnage() {
        id = -1;
        idJoueur = -1;
        nom = "";
        sprite = "";
        classe = new Classe();
        map = new Map();
        stuffArme = new Objet();
        stuffBotte = new Objet();
        stuffGant = new Objet();
        stuffJambe = new Objet();
        stuffTete = new Objet();
        stuffTorse = new Objet();
        faction = new Faction();
        guilde = new Guilde();
        titre = new Titre();

         direction = 0;
         moving = false;

    }

    public Personnage(int id, String nom, String sprite, int idJoueur, Classe classe, Map map, Objet stuffTete,
                      Objet stuffTorse, Objet stuffGant, Objet stuffJambe, Objet stuffBotte, Objet stuffArme,
                      Faction faction, Guilde guilde, Titre titre) {
        this.id = id;
        this.nom = nom;
        this.sprite = sprite;
        this.idJoueur = idJoueur;
        this.classe = classe;
        this.map = map;
        this.stuffTete = stuffTete;
        this.stuffTorse = stuffTorse;
        this.stuffGant = stuffGant;
        this.stuffJambe = stuffJambe;
        this.stuffBotte = stuffBotte;
        this.stuffArme = stuffArme;
        this.faction = faction;
        this.guilde = guilde;
        this.titre = titre;
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

    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public int getIdJoueur() {
        return idJoueur;
    }

    public void setIdJoueur(int idJoueur) {
        this.idJoueur = idJoueur;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Objet getStuffTete() {
        return stuffTete;
    }

    public void setStuffTete(Objet stuffTete) {
        this.stuffTete = stuffTete;
    }

    public Objet getStuffTorse() {
        return stuffTorse;
    }

    public void setStuffTorse(Objet stuffTorse) {
        this.stuffTorse = stuffTorse;
    }

    public Objet getStuffGant() {
        return stuffGant;
    }

    public void setStuffGant(Objet stuffGant) {
        this.stuffGant = stuffGant;
    }

    public Objet getStuffJambe() {
        return stuffJambe;
    }

    public void setStuffJambe(Objet stuffJambe) {
        this.stuffJambe = stuffJambe;
    }

    public Objet getStuffBotte() {
        return stuffBotte;
    }

    public void setStuffBotte(Objet stuffBotte) {
        this.stuffBotte = stuffBotte;
    }

    public Objet getStuffArme() {
        return stuffArme;
    }

    public void setStuffArme(Objet stuffArme) {
        this.stuffArme = stuffArme;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public Guilde getGuilde() {
        return guilde;
    }

    public void setGuilde(Guilde guilde) {
        this.guilde = guilde;
    }

    public Titre getTitre() {
        return titre;
    }

    public void setTitre(Titre titre) {
        this.titre = titre;
    }

    @Override
    public String toString() {
        return "Personnage{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", sprite='" + sprite + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", idJoueur=" + idJoueur + '\n' +
                ", classe=" + classe + '\n' +
                ", map='" + map + '\'' + '\n' +
                ", stuffTete=" + stuffTete + '\n' +
                ", stuffTorse=" + stuffTorse + '\n' +
                ", stuffGant=" + stuffGant + '\n' +
                ", stuffJambe=" + stuffJambe + '\n' +
                ", stuffBotte=" + stuffBotte + '\n' +
                ", stuffArme=" + stuffArme + '\n' +
                ", faction='" + faction + '\'' +
                ", guilde='" + guilde + '\'' +
                ", titre='" + titre + '\'' +
                ", direction='" + direction + '\'' +
                ", moving='" + moving + '\'' +
                '}';
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isMoving() {
        return moving;
    }
}
