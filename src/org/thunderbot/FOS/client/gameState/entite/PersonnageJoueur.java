/*
 * Personnage.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.gameState.entite;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.io.Serializable;

/**
 * Classe personnage, partager entre les clients et le serveurs
 * Infomration importantes a partager
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class PersonnageJoueur implements Serializable {

    public static final int HAUT = 0,
                   GAUCHE = 1,
                   BAS = 2,
                   DROITE = 3;

    protected boolean moving = false;

    protected float positionX; /** Position en X */
    protected float positionY; /** Position en Y */
    protected int direction;   /** Direction vers laquelle va le personnage */

    protected String pseudo; /** Pseudo du joueur / nom du personnage */

    protected String nomCarte; /** Carte sur laquelle se situe le personnage */

    private transient Animation[] animations = new Animation[8];

    public PersonnageJoueur() {
        positionX = 650;
        positionY = 400;
        direction = 0;
        pseudo = "";
    }

    public PersonnageJoueur(String pseudo, int direction, float x, float y) throws SlickException {
        this.pseudo = pseudo;
        this.direction = direction;
        this.positionX = x;
        this.positionY = y;

        SpriteSheet spriteSheet = new SpriteSheet("res/texture/sprite/joueur/personnage.png", 64, 64);
        this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
        this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
        this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
        this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
        this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
        this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
        this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
        this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);
    }

    public PersonnageJoueur(PersonnageJoueur personnageJoueur) throws SlickException {
        this.pseudo = personnageJoueur.pseudo;
        this.direction = personnageJoueur.direction;
        this.positionX = personnageJoueur.positionX;
        this.positionY = personnageJoueur.positionY;
        this.moving = personnageJoueur.moving;

        SpriteSheet spriteSheet = new SpriteSheet("res/texture/sprite/joueur/personnage.png", 64, 64);
        this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
        this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
        this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
        this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
        this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
        this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
        this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
        this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);
    }

    /**
     * Affichage du personnage sur le graphics passer en parametre
     * @param graphics graphic sur lequel afficher le personnage
     */
    public void render(Graphics graphics) {
        // Application d'un delta pour les collisions
        float positionAnimationX = positionX-32;
        float positionAnimationY = positionY-60;

        graphics.drawAnimation(animations[direction + (moving ? 4 : 0)],positionAnimationX , positionAnimationY);
        graphics.drawString(pseudo, positionX - graphics.getFont().getWidth(pseudo) / 2, positionY - 65);
    }

    /**
     * Charge une animations a partir d'une sprite sheet, en indiquant les début e l'annimation et la fin
     * @param spriteSheet
     * @param startX
     * @param endX
     * @param y
     * @return
     */
    protected Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 100);
        }
        return animation;
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

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public int getDirection() {
        return direction;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public String getNomCarte() {
        return this.nomCarte;
    }

    public String toString() {
        return "ServPersonnage(" + pseudo + ", " + direction + ", " + positionX + ", " + positionY + ", " + moving + ")";
    }

    public void setNomCarte(String nomCarte) {
        this.nomCarte = nomCarte;
    }
}
