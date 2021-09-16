package org.thunderbot.FOS.client.statiqueState.layout;

import org.newdawn.slick.Graphics;

public abstract class Layout {
    protected int longueur;
    protected int hauteur;
    protected float x;
    protected float y;
    protected String texte;

    public Layout(float x, float y, int longeur, int hauteur,  String texte) {
        this.longueur = longeur;
        this.hauteur = hauteur;
        this.x = x;
        this.y = y;
        this.texte = texte;
    }

    public void render(Graphics graphics) {
        graphics.drawRect(x, y, longueur, hauteur);
        graphics.drawString(texte, x + 5, y + (hauteur / 2 - 4));
    }

    /**
     * @param x
     * @param y
     * @return true si lees coordonnée sont comprise dans le bouton, false sinon
     */
    public boolean isInLayout(float x, float y) {
        return this.x < x && x < this.x + this.longueur
                && this.y < y && y < this.y + hauteur;
    }

    public int getLongueur() {
        return longueur;
    }

    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }
}
