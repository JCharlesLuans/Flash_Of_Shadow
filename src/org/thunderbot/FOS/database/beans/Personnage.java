/*
 * Personnage.java                      14/09/2021
 * Copyright et copyleft TNLag Corp.
 */
package org.thunderbot.FOS.database.beans;

import java.io.Serializable;

public class Personnage implements Serializable {

    private int id;
    private String nom;
    private String sprite;
    private int idJoueur;
    private Classe classe;
    private String map;
    private Objet stuffTete;
    private Objet stuffTorse;
    private Objet stuffGant;
    private Objet stuffJambe;
    private Objet stuffBotte;
    private Objet stuffArme;
    private String faction;
    private String guilde;
    private String titre;

    public Personnage() {
        idJoueur = -1;
        classe = null;
        map = "";
        stuffArme = null;
        stuffBotte = null;
        stuffGant = null;
        stuffJambe = null;
        stuffTete = null;
        stuffTorse = null;
        faction = "";
        guilde = "";
        titre = "";

    }

    public Personnage(int id, String nom, String sprite, int idJoueur, Classe classe, String map, Objet stuffTete, Objet stuffTorse, Objet stuffGant, Objet stuffJambe, Objet stuffBotte, Objet stuffArme, String faction, String guilde, String titre) {
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

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
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

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public String getGuilde() {
        return guilde;
    }

    public void setGuilde(String guilde) {
        this.guilde = guilde;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public String toString() {
        return "Personnage{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", sprite='" + sprite + '\'' +
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
                '}';
    }
}
