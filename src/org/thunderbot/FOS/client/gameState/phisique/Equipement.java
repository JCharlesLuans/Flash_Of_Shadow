/*
 * Equipement.java                  03/04/2023
 * TNLag Corp. Copyright and copyleft
 */
package org.thunderbot.FOS.client.gameState.phisique;

import org.thunderbot.FOS.database.beans.Objet;

/**
 * Equipement actif sur le joueur
 */
public class Equipement {
    private Objet stuffTete;
    private Objet stuffTorse;
    private Objet stuffGant;
    private Objet stuffJambe;
    private Objet stuffBotte;
    private Objet stuffArme;

    public Equipement(Objet stuffTete, Objet stuffTorse, Objet stuffGant, Objet stuffJambe, Objet stuffBotte, Objet stuffArme) {
        this.stuffTete = stuffTete;
        this.stuffTorse = stuffTorse;
        this.stuffGant = stuffGant;
        this.stuffJambe = stuffJambe;
        this.stuffBotte = stuffBotte;
        this.stuffArme = stuffArme;
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

    public int getAgilite() {
        return stuffTete.getStatAgilite() + stuffTorse.getStatAgilite() + stuffGant.getStatAgilite() +
                stuffJambe.getStatAgilite() + stuffBotte.getStatAgilite() + stuffArme.getStatAgilite();
    }

    public int getArmure() {
        return stuffTete.getStatArmure() + stuffTorse.getStatArmure() + stuffGant.getStatArmure() +
                stuffJambe.getStatArmure() + stuffBotte.getStatArmure() + stuffArme.getStatArmure();
    }

    public int getDexterite() {
        return stuffTete.getStatDexterite() + stuffTorse.getStatDexterite() + stuffGant.getStatDexterite() +
                stuffJambe.getStatDexterite() + stuffBotte.getStatDexterite() + stuffArme.getStatDexterite();
    }

    public int getEndurance() {
        return stuffTete.getStatEndurance() + stuffTorse.getStatEndurance() + stuffGant.getStatEndurance() +
                stuffJambe.getStatEndurance() + stuffBotte.getStatEndurance() + stuffArme.getStatEndurance();
    }

    public int getForce() {
        return stuffTete.getStatForce() + stuffTorse.getStatForce() + stuffGant.getStatForce() +
                stuffJambe.getStatForce() + stuffBotte.getStatForce() + stuffArme.getStatForce();
    }

    public int getIntelligence() {
        return stuffTete.getStatIntelligence() + stuffTorse.getStatIntelligence() + stuffGant.getStatIntelligence() +
                stuffJambe.getStatIntelligence() + stuffBotte.getStatIntelligence() + stuffArme.getStatIntelligence();
    }

    public int getSagesse() {
        return stuffTete.getStatSagesse() + stuffTorse.getStatSagesse() + stuffGant.getStatSagesse() +
                stuffJambe.getStatSagesse() + stuffBotte.getStatSagesse() + stuffArme.getStatSagesse();
    }
}
