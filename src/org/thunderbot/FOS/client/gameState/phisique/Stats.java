/*
 * Stats.java                 02/04/20223
 * Copyright et copyleft TNLag Corp.
 */
package org.thunderbot.FOS.client.gameState.phisique;

/**
 * Represente les statistique d'un personnage
 *
 * @author Jean-Charles Luans
 * @version 1.0
 */
public class Stats {

    private int vie;
    private int mana;
    private int mouvements;
    private int actions;


    private int armure;
    private int agilite;
    private int dexterite;
    private int intelligence;
    private int endurance;
    private int force;
    private int sagesse;

    public Stats() {

        this.vie = 0;
        this.mana = 0;
        this.mouvements = 0;
        this.armure = 0;

        this.agilite = 0;
        this.armure = 0;
        this.dexterite = 0;
        this.intelligence = 0;
        this.endurance = 0;
        this.force = 0;
        this.sagesse = 0;
    }

    public Stats(int vie, int mana, int mouvements, int actions, int agilite, int armure, int dexterite, int intelligence, int endurance, int force, int sagesse) {
        this.vie = vie;
        this.mana = mana;
        this.mouvements = mouvements;
        this.actions = actions;

        this.agilite = agilite;
        this.armure = armure;
        this.dexterite = dexterite;
        this.intelligence = intelligence;
        this.endurance = endurance;
        this.force = force;
        this.sagesse = sagesse;
    }

    public Stats(int agilite, int armure, int dexterite, int intelligence, int endurance, int force, int sagesse) {
        this.vie = 0;
        this.mana = 0;
        this.mouvements = 0;
        this.actions = 0;

        this.agilite = agilite;
        this.armure = armure;
        this.dexterite = dexterite;
        this.intelligence = intelligence;
        this.endurance = endurance;
        this.force = force;
        this.sagesse = sagesse;
    }

    public int getArmure() {
        return armure;
    }

    public void setArmure(int armure) {
        this.armure = armure;
    }

    public int getAgilite() {
        return agilite;
    }

    public void setAgilite(int agilite) {
        this.agilite = agilite;
    }

    public int getDexterite() {
        return dexterite;
    }

    public void setDexterite(int dexterite) {
        this.dexterite = dexterite;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public int getSagesse() {
        return sagesse;
    }

    public void setSagesse(int sagesse) {
        this.sagesse = sagesse;
    }

    public int calculVie() {
        vie = endurance;
        return vie;
    }

    public int calculMana() {
        mana = sagesse;
        return mana;
    }

    public int calculMouvement() {
        mouvements = agilite;
        return mana;
    }

    public int calculActions() {
        actions = dexterite;
        return actions;
    }


}
