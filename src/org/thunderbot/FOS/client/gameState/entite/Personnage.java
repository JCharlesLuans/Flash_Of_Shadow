/*
 * Personnage.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.gameState.entite;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.thunderbot.FOS.client.gameState.world.Carte;

/**
 * Personnage du client
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Personnage extends ServPersonnage {

    private Animation[] animations = new Animation[8];

    public Personnage() throws SlickException {
        super();
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

    public void render(Graphics graphics) {

        // Application d'un delta pour les collisions
        float positionAnimationX = positionX-32;
        float positionAnimationY = positionY-60;

        graphics.drawAnimation(animations[direction + (moving ? 4 : 0)],positionAnimationX , positionAnimationY);
    }

    /**
     * Update le du client
     * @param carte Map sur laquelle evolue le personnage
     * @param delta
     */
    public void update(Carte carte, int delta) {

        float futurX,
              futurY;

        if (this.moving) {
            futurX = getFuturX(delta);
            futurY = getFuturY(delta);

            if (carte.isCollision(futurX, futurY)) {
                System.out.println("COLLISION");

                // TODO DEBUG
                System.out.println("Futur X = " + futurX);
                System.out.println("Futur Y = " + futurY);

            }  else {
                positionX = futurX;
                positionY = futurY;
            }

        }
    }

    /**
     * @param delta
     * @return la position en X aprés déplacement
     */
    private float getFuturX(int delta) {

        float futurX = positionX;

        switch (this.direction) {
            case DROITE :
                futurX += .1f * delta;
                break;
            case GAUCHE :
                futurX -= .1f * delta;
                break;
        }

        return futurX;
    }

    /**
     * @param delta
     * @return la position en Y aprés déplacement
     */
    private float getFuturY(int delta) {

        float futurY = positionY;

        switch (this.direction) {
            case BAS :
                futurY += .1f * delta;
                break;
            case HAUT :
                futurY -= .1f * delta;
                break;
        }

        return futurY;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}
