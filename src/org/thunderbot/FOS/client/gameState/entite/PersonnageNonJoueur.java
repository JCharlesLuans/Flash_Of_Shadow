/*
 * Mob.java                 22/09/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.gameState.entite;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.thunderbot.FOS.client.gameState.world.Carte;
import org.thunderbot.FOS.database.beans.PNJ;

/**
 * Représentation d'un mob, qu'il soit passif ou agressif
 *
 * @author Jean-Charles Luans
 */
public class PersonnageNonJoueur extends Personnage{

    private int timer;

    public PersonnageNonJoueur(PNJ pnj) throws SlickException {
        this.moving = true;
        this.positionX = 640;
        this.positionY = 400;
        this.nom = pnj.getNom();
        SpriteSheet spriteSheet = new SpriteSheet("res/texture/pnj/" + pnj.getSprite(), 64, 64 );
        loadAnimation(spriteSheet);
    }

    @Override
    public void update(Carte carte, int delta) {
        super.update(carte, delta);

        timer += delta;
        if (timer >= 1500) {
            timer = 0;
            collision = true;
        }

        if (collision) {
            direction = Personnage.HAUT + (int)(Math.random() * ((Personnage.DROITE - Personnage.HAUT) + 1));
        }

    }
}
