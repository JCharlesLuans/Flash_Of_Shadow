/*
 * Case.java                        24/09/21
 * Copyright TNLag Corp.
 */
package org.thunderbot.FOS.client.combatState;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Représente une case sur laquelle un personnage peux etre positionné
 */
public class Case {
    private int x; /** Position coin sup gauche */
    private int y; /** Position coin sup gauche */
    private int longueur; /** Longueur d'un coté*/
    private int id; /** Id permettant de reperer la case dans une carte */

    private boolean colorerRouge;
    private boolean colorerVert;
    private boolean nonColorer;

    public Case(int id, int x, int y, int longueur) {
        this.x = x;
        this.y = y;
        this.longueur = longueur;
        this.id = id;
        nonColorer = true;
        colorerVert = colorerRouge = false;
    }

    public void render(Graphics graphics) {
        Color couleurDeBase = graphics.getColor();
        if (nonColorer) {
            graphics.setColor(new Color(0, 0, 0));
            graphics.drawRect(x, y, longueur, longueur);
        } else if (colorerVert) {
            graphics.setColor(new Color(0, 255, 0));
            graphics.fillRect(x, y, longueur, longueur);
        } else {
            graphics.setColor(new Color(255, 0, 0));
            graphics.fillRect(x, y, longueur, longueur);
        }
        graphics.setColor(couleurDeBase);
    }

    public int getId() {
        return id;
    }

    public boolean inCase(int x, int y) {
        return this.x < x && x < this.x + longueur && this.y < y && y < this.y + longueur;
    }

    public void setColorRouge(boolean b) {
        colorerRouge = b;
        colorerVert = nonColorer = false;
    }

    public void setColorVert(boolean b) {
        colorerVert = b;
        colorerRouge = nonColorer = false;
    }

    public void setNonColorer(boolean b) {
        nonColorer = b;
        colorerVert = colorerRouge = false;
    }
}
