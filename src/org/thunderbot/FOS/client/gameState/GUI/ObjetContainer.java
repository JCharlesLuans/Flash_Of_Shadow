package org.thunderbot.FOS.client.gameState.GUI;

import org.newdawn.slick.Graphics;
import org.thunderbot.FOS.database.beans.Objet;


/**
 * Cadre invisible d'un emplacement de GUI pour gerer l'affichage d'un objet
*/
public class ObjetContainer {

    private int x;
    private int y;
    private int longueur;
    private int hauteur;

    private Objet objet; /** Objet contenu dans le conteuneur */

    public ObjetContainer(int x, int y, int longueur, int hauteur) {
        this.x = x;
        this.y = y;
        this.longueur = longueur;
        this.hauteur = hauteur;
    }

    public void render(Graphics graphics) {
        graphics.drawRect(x, y, longueur, hauteur);
    }

    /** Attribut un objet au container */
    public void setObjet(Objet objet) {
        this.objet = objet;
    }

    public void mouseMouved(int y, int x) {
        if (this.x < x && x < this.x + this.longueur
        && this.y < y && y < this.y + this.hauteur) {
            System.out.println(objet.toString());
        }
    }
}
