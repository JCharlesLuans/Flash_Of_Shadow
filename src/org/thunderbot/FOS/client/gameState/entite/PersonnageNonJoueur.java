/*
 * Mob.java                 22/09/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.gameState.entite;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.thunderbot.FOS.database.beans.PNJ;

/**
 * Représentation d'un mob, qu'il soit passif ou agressif
 *
 * @author Jean-Charles Luans
 */
public class PersonnageNonJoueur extends Personnage{

    public PersonnageNonJoueur(PNJ pnj) throws SlickException {
        this.positionX = 640;
        this.positionY = 400;
        this.pseudo = pnj.getNom();
        SpriteSheet spriteSheet = new SpriteSheet("res/texture/pnj/" + pnj.getSprite(), 64, 64 );
        loadAnimation(spriteSheet);
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
        // Application d'un delta pour les collisions
        float positionAnimationX = positionX - 32;
        float positionAnimationY = positionY - 60;

        graphics.drawAnimation(animations[direction + (moving ? 4 : 0)], positionAnimationX, positionAnimationY);
        graphics.drawString(pseudo, positionX - graphics.getFont().getWidth(pseudo) / 2, positionY - 65);
    }
}
