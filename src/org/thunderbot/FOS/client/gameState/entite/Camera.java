/*
 * Camera.java             30/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.gameState.entite;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.thunderbot.FOS.client.gameState.world.Carte;

/**
 * Camera flottante qui suit le personnage
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Camera {

    private PersonnageJoueurClient personnageJoueurClient; // Personnage que doit suivre la camera

    private float positionX;  // Position X du centre de la camera
    private float positionY;  // Position Y du centre de la camera

    /**
     * Initialisation d'une nouvelle camera avec un personnage null
     */
    public Camera() {
        personnageJoueurClient = null;
    }

    /**
     * Iitialisation d'une nouvelle camera avec un personnage passé en paramettre
     * @param newPersonnageJoueurClient personnage que suivra la camera
     */
    public Camera(PersonnageJoueurClient newPersonnageJoueurClient) {
        this.personnageJoueurClient = newPersonnageJoueurClient;
        this.positionX = personnageJoueurClient.getPositionX();
        this.positionY = personnageJoueurClient.getPositionY();
    }

    /**
     * Actualise et deplace la camera en fonction du joueur
     */
    public void update(GameContainer container, Carte carte) {

        int largeurCarte = carte.getWidth() * 32; // Récupere la demi largeur de l'ecran
        int hauteurCarte = carte.getHeight()  * 32; // Récupere la demi hauteur de l'ecran
        int demiEcranLarg = container.getWidth() / 2;
        int demiEcranHaut = container.getHeight() / 2;

        updateChangementCarte(carte, demiEcranLarg, demiEcranHaut);

        if (positionX < largeurCarte - demiEcranLarg) {

            if (positionX > demiEcranLarg) {
                positionX = personnageJoueurClient.getPositionX();
            } else if (personnageJoueurClient.getDirection() == Personnage.DROITE && personnageJoueurClient.getPositionX() > demiEcranLarg) {
                positionX = personnageJoueurClient.getPositionX();
            }
        } else if (personnageJoueurClient.getDirection() == Personnage.GAUCHE && personnageJoueurClient.getPositionX() < largeurCarte - demiEcranLarg) {
            positionX = personnageJoueurClient.getPositionX();
        }

        if (positionY < hauteurCarte - demiEcranHaut) {

            if (positionY > demiEcranHaut) {
                positionY = personnageJoueurClient.getPositionY();
            } else if (personnageJoueurClient.getDirection() == Personnage.BAS && personnageJoueurClient.getPositionY() > demiEcranHaut) {
                positionY = personnageJoueurClient.getPositionY();
            }
        } else if (personnageJoueurClient.getDirection() == Personnage.HAUT && personnageJoueurClient.getPositionY() < hauteurCarte - demiEcranHaut) {
            positionY = personnageJoueurClient.getPositionY();
        }

        personnageJoueurClient.getGui().setX((int) positionX);
        personnageJoueurClient.getGui().setY((int) positionY);
    }

    private void updateChangementCarte(Carte carte, int demiEcranLarg, int demiEcranHaut) {

        int hauteurCarte = carte.getHeight() * 32;
        int largeurCarte = carte.getWidth() * 32;

        float largeurEnMoins;
        float largeurEnTrop;
        float hauteurEnMoins;
        float hauteurEnTrop;

        if (carte.getChangeCarte()) {
            /* Recadrage de la camera */
            // Déplacement du centre de la camera au coordonnée du joueur
            positionX = personnageJoueurClient.getPositionX() - 32;
            positionY = personnageJoueurClient.getPositionY() + 60;

            // Application d'un delta si la camera dépasse la limite de la carte

            largeurEnMoins = positionX - demiEcranLarg;
            largeurEnTrop  = positionX + demiEcranLarg;

            // Recalage en X
            if (largeurEnMoins <= 0) {
                positionX = positionX - largeurEnMoins;
            } else if (largeurEnTrop > largeurCarte) {
                positionX = largeurCarte - demiEcranLarg;

            }

            hauteurEnMoins = positionY - demiEcranHaut;
            hauteurEnTrop  = positionY + demiEcranHaut;

            // Recalage en Y
            if (hauteurEnMoins <= 0) {
                positionY = positionY - hauteurEnMoins;
            } else if (hauteurEnTrop > hauteurCarte) {
                positionY = hauteurCarte - demiEcranHaut;
            }

            carte.setChangeCarte(false);
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
