/*
 * Case.java                        24/09/21
 * Copyright TNLag Corp.
 */
package org.thunderbot.FOS.client.combatState;

import org.newdawn.slick.Graphics;

/**
 * Représente une case sur laquelle un personnage peux etre positionné
 */
public class Case {
    private int x; /** Position coin sup gauche */
    private int y; /** Position coin sup gauche */
    private int longueur; /** Longueur d'un coté*/
    private int id; /** Id permettant de reperer la case dans une carte */

    public Case(int id, int x, int y, int longueur) {
        this.x = x;
        this.y = y;
        this.longueur = longueur;
        this.id = id;
    }

    public void render(Graphics graphics) {
        graphics.drawRect(x, y, longueur, longueur);
    }

    public boolean mouseClicked(int button, int x, int y, int nbClick) {
        return this.x < x && x < this.x + longueur && this.y < y && y < this.y + longueur;
    }

    public int getId() {
        return id;
    }
}
