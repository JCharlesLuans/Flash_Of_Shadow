/*
 * Personnage.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.gameState.entite;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.thunderbot.FOS.client.gameState.phisique.Equipement;
import org.thunderbot.FOS.client.gameState.phisique.Stats;
import org.thunderbot.FOS.database.beans.Objet;

/**
 * Personnage jouer par différent joueur, qu'ils soient se client la ou des joueur distants
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class PersonnageJoueur extends Personnage {

    protected String nomCarte;
    protected Equipement equipement;

    public PersonnageJoueur() {
        positionX = 650;
        positionY = 400;
        direction = 0;
        nom = "";
        stats = new Stats();
    }

    public PersonnageJoueur(int id, String pseudo, int direction, float x, float y, String sprite) throws SlickException {
        this.id = id;
        this.nom = pseudo;
        this.direction = direction;
        this.positionX = x;
        this.positionY = y;

        SpriteSheet spriteSheet = new SpriteSheet("res/texture/sprite/joueur/" + sprite, 64, 64);
        loadAnimation(spriteSheet);
    }

    public void calculStats() {
        // Calcul des stats secondaire
        stats.setAgilite(equipement.getAgilite());
        stats.setArmure(equipement.getArmure());
        stats.setDexterite(equipement.getDexterite());
        stats.setEndurance(equipement.getEndurance());
        stats.setForce(equipement.getForce());
        stats.setIntelligence(equipement.getIntelligence());
        stats.setSagesse(equipement.getSagesse());

        // Calcul des stas primaire
        stats.calculVie();
        stats.calculMana();
        stats.calculMouvement();
        stats.calculActions();
    }

    public String getNomCarte() {
        return this.nomCarte;
    }

    public void setNomCarte(String nomCarte) {
        this.nomCarte = nomCarte;
    }

    public Equipement getEquipement() {
        return equipement;
    }
}
