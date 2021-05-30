/*
 * Personnage.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.gameState.entite;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Personnage du client
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Personnage extends ServPersonnage {

    private boolean moving = false;
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
        graphics.drawAnimation(animations[direction + (moving ? 4 : 0)], positionX, positionY);
    }

    public void update(int delta) {
        if (this.moving) {
            switch (this.direction) {
                case 0: this.positionY -= .1f * delta; break;
                case 1: this.positionX -= .1f * delta; break;
                case 2: this.positionY += .1f * delta; break;
                case 3: this.positionX += .1f * delta; break;
            }
        }
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}
