/*
 * Mob.java                 22/09/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.gameState.entite;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.thunderbot.FOS.client.gameState.MapGameState;
import org.thunderbot.FOS.client.gameState.world.Carte;
import org.thunderbot.FOS.client.statiqueState.layout.FenetrePopUpChoix;
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

    public void render(Graphics graphics) {
        super.render(graphics);
    }

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

    public void mouseClicked(MapGameState mapGameState, int x, int y) {

        x += 32;
        y += 60;

        if (this.positionX - 32 < x && x < positionX + 92 && this.positionY - 32 < y && y  < positionY + 92) {
            mapGameState.setCombat(true);
        }
    }
}
