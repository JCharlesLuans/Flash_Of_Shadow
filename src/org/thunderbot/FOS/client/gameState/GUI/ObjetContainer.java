package org.thunderbot.FOS.client.gameState.GUI;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.thunderbot.FOS.client.statiqueState.layout.ImageFlottante;
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

    private ImageFlottante imageFlottante;

    public ObjetContainer(int x, int y, int longueur, int hauteur) throws SlickException {
        this.x = x;
        this.y = y;
        this.longueur = longueur;
        this.hauteur = hauteur;
        imageFlottante = new ImageFlottante("res/menuState/gui/fondTexte.png");
    }

    public void render(Graphics graphics) {
        graphics.drawRect(x, y, longueur, hauteur);
        imageFlottante.render(graphics);
    }

    /** Attribut un objet au container */
    public void setObjet(Objet objet) {
        this.objet = objet;
        imageFlottante.setTexte(objet.getNom() + " :\n" + objet.getDesc());
    }

    public void mouseMouved(int y, int x) {
        if (this.x < x && x < this.x + this.longueur
        && this.y < y && y < this.y + this.hauteur) {
            imageFlottante.setVisible(true);
            imageFlottante.setX(x);
            imageFlottante.setY(y);
        } else {
            imageFlottante.setVisible(false);
        }
    }
}
