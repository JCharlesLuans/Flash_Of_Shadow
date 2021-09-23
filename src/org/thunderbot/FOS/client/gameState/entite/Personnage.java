package org.thunderbot.FOS.client.gameState.entite;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

/**
 * Représente un personnage, qu'il soit joueur ou non joueur.
 */
public class Personnage {
    public static final int HAUT = 0;
    public static final int GAUCHE = 1;
    public static final int BAS = 2;
    public static final int DROITE = 3;

    /**
     * Indique si le personnage se deplace ou non
     */
    protected boolean moving = false;

    /**
     * Position en X
     */
    protected float positionX;

    /**
     * Position en Y
     */
    protected float positionY;

    /**
     * Direction vers laquelle va le personnage
     */
    protected int direction;

    /**
     * Carte sur laquelle se situe le personnage
     */
    protected String pseudo;

    /**
     * Animation générer a partir du sprite du personnage
     */
    protected Animation[] animations = new Animation[8];

    /**
     * Charge une animations a partir d'une sprite sheet, en indiquant les début de l'annimation et la fin
     *
     * @param spriteSheet
     * @param startX
     * @param endX
     * @param y
     * @return
     */
    public static Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 100);
        }
        return animation;
    }

    public void loadAnimation(SpriteSheet spriteSheet) {
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
     *
     * @param graphics graphic sur lequel afficher le personnage
     */
    public void render(Graphics graphics) {
        // Application d'un delta pour les collisions
        float positionAnimationX = positionX - 32;
        float positionAnimationY = positionY - 60;

        graphics.drawAnimation(animations[direction + (moving ? 4 : 0)], positionAnimationX, positionAnimationY);
        graphics.drawString(pseudo, positionX - graphics.getFont().getWidth(pseudo) / 2, positionY - 65);
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

    public String toString() {
        return "ServPersonnage(" + pseudo + ", " + direction + ", " + positionX + ", " + positionY + ", " + moving + ")";
    }
}
