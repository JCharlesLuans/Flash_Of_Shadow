/*
 * Stats.java                 02/04/20223
 * Copyright et copyleft TNLag Corp.
 */
package org.thunderbot.FOS.client.gameState.phisique;

import java.io.Serializable;

/**
 * Represente les statistique d'un personnage
 *
 * @author Jean-Charles Luans
 * @version 1.0
 */
public class Stats implements Serializable {

    private int vieMax;
    private int manaMax;
    private int mouvementsMax;
    private int actionsMax;

    private int vieRestante;
    private int manaRestante;
    private int mouvementsRestants;
    private int actionsRestantes;


    private int armure;
    private int agilite;
    private int dexterite;
    private int intelligence;
    private int endurance;
    private int force;
    private int sagesse;

    public Stats() {

        this.vieMax = 0;
        this.manaMax = 0;
        this.mouvementsMax = 0;
        this.armure = 0;

        this.agilite = 0;
        this.armure = 0;
        this.dexterite = 0;
        this.intelligence = 0;
        this.endurance = 0;
        this.force = 0;
        this.sagesse = 0;
    }

    public Stats(int vieMax, int manaMax, int mouvementsMax, int actionsMax, int agilite, int armure, int dexterite, int intelligence, int endurance, int force, int sagesse) {
        this.vieMax = this.vieRestante = vieMax;
        this.manaMax = this.manaRestante = manaMax;
        this.mouvementsMax = this.mouvementsRestants = mouvementsMax;
        this.actionsMax = this.actionsRestantes = actionsMax;

        this.agilite = agilite;
        this.armure = armure;
        this.dexterite = dexterite;
        this.intelligence = intelligence;
        this.endurance = endurance;
        this.force = force;
        this.sagesse = sagesse;
    }

    public Stats(int agilite, int armure, int dexterite, int intelligence, int endurance, int force, int sagesse) {
        this.vieMax = 0;
        this.manaMax = 0;
        this.mouvementsMax = 0;
        this.actionsMax = 0;

        this.agilite = agilite;
        this.armure = armure;
        this.dexterite = dexterite;
        this.intelligence = intelligence;
        this.endurance = endurance;
        this.force = force;
        this.sagesse = sagesse;

        calculVie();
        calculMana();
        calculMouvement();
        calculActions();
    }

    /**
     * Calcul de la Vie maximale en fonction de la stats endurances
     * @return la vie max
     */
    public int calculVie() {
        vieMax = vieRestante = endurance;
        return vieMax;
    }

    /**
     * Calcul de la Mana maximale en fonction de la stats sagesse
     * @return la mana max
     */
    public int calculMana() {
        manaMax = manaRestante = sagesse;
        return manaMax;
    }

    /**
     * Calcul de la Vie maximale en fonction de la stats agilite
     * @return la mouvement max
     */
    public int calculMouvement() {
        mouvementsMax = mouvementsRestants = agilite / 20 + 2;
        return manaMax;
    }

    /**
     * Calcul de la Vie maximale en fonction de la stats dexterite
     * @return action max
     */
    public int calculActions() {
        actionsMax = actionsRestantes = dexterite / 20 + 2;
        return actionsMax;
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

    public int getVieMax() {
        return vieMax;
    }

    public void setVieMax(int vieMax) {
        this.vieMax = vieMax;
    }

    public int getManaMax() {
        return manaMax;
    }

    public void setManaMax(int manaMax) {
        this.manaMax = manaMax;
    }

    public int getMouvementsMax() {
        return mouvementsMax;
    }

    public void setMouvementsMax(int mouvementsMax) {
        this.mouvementsMax = mouvementsMax;
    }

    public int getActionsMax() {
        return actionsMax;
    }

    public void setActionsMax(int actionsMax) {
        this.actionsMax = actionsMax;
    }

    public int getVieRestante() {
        return vieRestante;
    }

    public void setVieRestante(int vieRestante) {
        this.vieRestante = vieRestante;
    }

    public int getManaRestante() {
        return manaRestante;
    }

    public void setManaRestante(int manaRestante) {
        this.manaRestante = manaRestante;
    }

    public int getMouvementsRestants() {
        return mouvementsRestants;
    }

    public void setMouvementsRestants(int mouvementsRestants) {
        this.mouvementsRestants = mouvementsRestants;
    }

    public int getActionsRestantes() {
        return actionsRestantes;
    }

    public void setActionsRestantes(int actionsRestantes) {
        this.actionsRestantes = actionsRestantes;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "vieMax=" + vieMax +
                ", manaMax=" + manaMax +
                ", mouvementsMax=" + mouvementsMax +
                ", actionsMax=" + actionsMax +
                ", vieRestante=" + vieRestante +
                ", manaRestante=" + manaRestante +
                ", mouvementsRestants=" + mouvementsRestants +
                ", actionsRestantes=" + actionsRestantes +
                ", vieRestante=" + vieRestante +
                ", manaRestante=" + manaRestante +
                ", mouvementsRestants=" + mouvementsRestants +
                ", actionsRestantes=" + actionsRestantes +
                ", armure=" + armure +
                ", agilite=" + agilite +
                ", dexterite=" + dexterite +
                ", intelligence=" + intelligence +
                ", endurance=" + endurance +
                ", force=" + force +
                ", sagesse=" + sagesse +
                '}';
    }
}
