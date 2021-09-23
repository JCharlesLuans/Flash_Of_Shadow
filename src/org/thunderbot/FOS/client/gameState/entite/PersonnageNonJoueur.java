/*
 * Mob.java                 22/09/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.gameState.entite;

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
        this.moving = true;
        this.positionX = 640;
        this.positionY = 400;
        this.nom = pnj.getNom();
        SpriteSheet spriteSheet = new SpriteSheet("res/texture/pnj/" + pnj.getSprite(), 64, 64 );
        loadAnimation(spriteSheet);
    }

}
