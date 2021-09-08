/* Bouton.java             08/09/2021
 * Copyright et copyleft ThunderBot
 */
package org.thunderbot.FOS.client.statiqueState.layout;

import org.newdawn.slick.Graphics;

public class Bouton {

    private int longueur;
    private int hauteur;
    private float x;
    private float y;
    private String texte;

    public Bouton(int longeur, int hauteur, float x, float y, String texte) {
        this.longueur = longeur;
        this.hauteur = hauteur;
        this.x = x;
        this.y = y;
        this.texte = texte;
    }

    public void render(Graphics graphics) {
        graphics.drawRect(x, y, longueur, hauteur);
        graphics.drawString(texte, x+5, y+(hauteur/2-4));
    }

    /**
     * @param x
     * @param y
     * @return true si lees coordonnée sont comprise dans le bouton, false sinon
     */
    public boolean isInBouton(float x, float y) {
        return this.x < x && x < this.x + this.longueur && this.y < y && y < this.y + longueur;
    }

}
