/*
 * Personnage.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.gameState.entite;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.thunderbot.FOS.client.gameState.GUI.Gui;
import org.thunderbot.FOS.client.gameState.world.Carte;
import org.thunderbot.FOS.client.network.Client;

/**
 * Personnage du client
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Personnage extends PersonnageJoueur {

    /** Client pour la connection avec le serveur */
    Client client;

    private Gui gui;

    /** Annimations du personnages */
    private Animation[] animations = new Animation[8];

    /**  Inidique si le personnage est sur un escalier ou pas */
    private boolean escalierDroite,
                    escalierGauche;

    /**
     * Nouveau personnage joueur de ce client
     * @throws SlickException
     * @param client de jeu pour la communication avec le serveur
     */
    public Personnage(Client client) throws SlickException {
        super();

        this.client = client;

        SpriteSheet spriteSheet = new SpriteSheet("res/texture/sprite/joueur/" + client.getPersonnage().getSprite(), 64, 64);
        this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
        this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
        this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
        this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
        this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
        this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
        this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
        this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);
    }

    /**
     * Affichage du personnage jouuer sur le client
     * @param graphics graphic sur lequel afficher le personnage
     */
    public void render(Graphics graphics) {

        // Application d'un delta pour les collisions
        float positionAnimationX = positionX-32;
        float positionAnimationY = positionY-60;

        graphics.drawAnimation(animations[direction + (moving ? 4 : 0)],positionAnimationX , positionAnimationY);
    }

    /**
     * Update le du client
     * @param carte Map sur laquelle evolue le personnage
     * @param delta
     */
    public void update(Carte carte, int delta) {

        float futurX,
                futurY;

        updateTrigger(carte);

        if (this.moving) {

            // Calcul des de la futur position
            futurX = getFuturX(delta);
            futurY = getFuturY(delta);

            // Gestion des collisions avec un mur
            if (!carte.isCollision(futurX, futurY)) {
                positionX = futurX;
                positionY = futurY;
            }

        }

        client.getPersonnage().setX(positionX);
        client.getPersonnage().setY(positionY);
        client.getPersonnage().setDirection(direction);
        client.getPersonnage().setMoving(moving);

    }

    /**
     * Gere les updates de triggerr
     */
    private void updateTrigger(Carte carte) {

        escalierDroite = escalierGauche = false;

        for (int objectID = 0; objectID < carte.getObjectCount(); objectID++) {

            if (carte.isInTrigger(positionX, positionY, objectID)) {
                escalierGauche = "escalierGauche".equals(carte.getObjectType(objectID));
                escalierDroite = "escalierDroite".equals(carte.getObjectType(objectID));

                if ("changementMap".equals(carte.getObjectType(objectID))) {
                    String newMap = carte.getObjectProperty(objectID, "destiMap", "undefine");
                    positionX = Integer.parseInt(carte.getObjectProperty(objectID, "destiX", "undefinine"));
                    positionY = Integer.parseInt(carte.getObjectProperty(objectID, "destiY", "undefinine"));

                    try {
                        nomCarte = newMap;
                        carte.changeMap(newMap, client);
                        carte.setChangeCarte(true);
                    } catch (SlickException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
    }



    /**
     * @param delta
     * @return la position en X aprés déplacement
     */
    private float getFuturX(int delta) {

        float futurX = positionX;

        switch (this.direction) {
            case DROITE :
                futurX += .1f * delta;
                break;
            case GAUCHE :
                futurX -= .1f * delta;
                break;
        }

        return futurX;
    }

    /**
     * @param delta
     * @return la position en Y aprés déplacement
     */
    private float getFuturY(int delta) {

        float futurY = positionY;

        switch (this.direction) {
            case BAS :
                futurY += .1f * delta;
                break;
            case HAUT :
                futurY -= .1f * delta;
                break;

            case GAUCHE:
                if (escalierDroite) {
                    futurY = positionY + .1f * delta;
                }
                if (escalierGauche) {
                    futurY = positionY - .1f * delta;
                }
                break;

            case 3:
                if (escalierGauche) {
                    futurY = positionY + .1f * delta;
                }
                if (escalierDroite) {
                    futurY = positionY - .1f * delta;
                }
                break;
        }

        return futurY;
    }

    /**
     * @param moving nouvelle état de moving
     */
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setGui(Gui gui) {
        this.gui = gui;
    }

    public Gui getGui() {
        return gui;
    }
}