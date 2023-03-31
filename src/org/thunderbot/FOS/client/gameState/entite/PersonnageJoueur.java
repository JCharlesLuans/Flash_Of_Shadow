/*
 * Personnage.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.gameState.entite;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Personnage jouer par différent joueur, qu'ils soient se client la ou des joueur distants
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class PersonnageJoueur extends Personnage {

    private int id;

    protected String nomCarte;

    public PersonnageJoueur() {
        positionX = 650;
        positionY = 400;
        direction = 0;
        nom = "";
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

    /**
     * Mise a jour réseaux
     * @param personnageJoueur
     */
    public void miseAJour(PersonnageJoueur personnageJoueur) {
        this.positionX = personnageJoueur.positionX;
        this.positionY = personnageJoueur.positionY;
        this.direction = personnageJoueur.direction;
        this.moving = personnageJoueur.moving;
        this.nomCarte = personnageJoueur.nomCarte;
    }

    public String getNomCarte() {
        return this.nomCarte;
    }

    public void setNomCarte(String nomCarte) {
        this.nomCarte = nomCarte;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
}
