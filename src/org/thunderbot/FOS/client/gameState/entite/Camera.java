/*
 * Camera.java             30/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.gameState.entite;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.thunderbot.FOS.client.gameState.world.Map;

/**
 * Camera flottante qui suit le personnage
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Camera {

    private Personnage personnage; // Personnage que doit suivre la camera

    private float positionX;  // Position X de la camera
    private float positionY;  // Position Y de la camera

    /**
     * Initialisation d'une nouvelle camera avec un personnage null
     */
    public Camera() {
        personnage = null;
    }

    /**
     * Iitialisation d'une nouvelle camera avec un personnage passé en paramettre
     * @param newPersonnage personnage que suivra la camera
     */
    public Camera(Personnage newPersonnage) {
        this.personnage = newPersonnage;
        this.positionX = personnage.getPositionX();
        this.positionY = personnage.getPositionY();
    }

    /**
     * Actualise et deplace la camera en fonction du joueur
     */
    public void update(GameContainer container, Map map) {

        int largeurCarte = map.getWidth() * 32; // Récupere la demi largeur de l'ecran
        int hauteurCarte = map.getHeight()  * 32; // Récupere la demi hauteur de l'ecran
        int demiEcranLarg = container.getWidth() / 2;
        int demiEcranHaut = container.getHeight() / 2;


        if (positionX < largeurCarte - demiEcranLarg) {

            if (positionX > demiEcranLarg) {
                positionX = personnage.getPositionX();
            } else if (personnage.getDirection() == ServPersonnage.DROITE && personnage.getPositionX() > demiEcranLarg) {
                positionX = personnage.getPositionX();
            }
        } else if (personnage.getDirection() == ServPersonnage.GAUCHE && personnage.getPositionX() < largeurCarte - demiEcranLarg) {
            positionX = personnage.getPositionX();
        }

        if (positionY < hauteurCarte - demiEcranHaut) {

            if (positionY > demiEcranHaut) {
                positionY = personnage.getPositionY();
            } else if (personnage.getDirection() == ServPersonnage.BAS && personnage.getPositionY() > demiEcranHaut) {
                positionY = personnage.getPositionY();
            }
        } else if (personnage.getDirection() == ServPersonnage.HAUT && personnage.getPositionY() < hauteurCarte - demiEcranHaut) {
            positionY = personnage.getPositionY();
        }

    }

    /**
     * Affiche la camera sur le graphic G
     * @param gameContainer container du jeu
     * @param graphics graphic d'affichage
     */
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.translate(gameContainer.getWidth()  / 2f -  positionX,
                           gameContainer.getHeight() / 2f -  positionY);
    }

}
