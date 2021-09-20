/*
 * FenetrePopUp.java                 19/09/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.statiqueState.layout;

import org.newdawn.slick.*;

import java.util.ArrayList;

/**
 * Affiche un message d'avertissement
 *
 * @author Jean-Charles Luans
 */
public class FenetrePopUp {

    public static final int CODE_RETOUR_DEFAUT = -1;
    public static final int CODE_RETOUR_FERMETURE = 0;
    public static final int CODEE_RETOUR_OK = 1;

    private static final int CROIX_X = 597;
    private static final int CROIX_X_FIN = 624;
    private static final int CROIX_Y = 14;
    private static final int CROIX_Y_FIN = 36;
    private static final int DELTA_START_MESSAGE = 50;

    private int y;
    private int x;

    private String message;
    private boolean show;
    private Image fond;

    private Sound son;

    public FenetrePopUp(GameContainer gameContainer, String message) throws SlickException {

        son = new Sound("res/menuState/son/ouvertureFenetre.wav");

        this.message = message;
        fond = new Image("res/menuState/gui/fenetrePopUp.png");
        show = false;

        x = gameContainer.getWidth() / 2 - fond.getWidth() / 2;
        y = gameContainer.getHeight() / 2 - fond.getHeight() / 2;
    }

    public void render(Graphics graphics) {
        if (show) {
            graphics.drawImage(fond, x, y);
            graphics.drawString(message, x + DELTA_START_MESSAGE, y + DELTA_START_MESSAGE);

        }
    }

    public void setShow(boolean show) {
        this.show = show;
        son.play();
    }

    public boolean isShow() {
        return show;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int mouseClicked(int button, int x, int y, int clickCount) {

        int codeRetour = CODE_RETOUR_DEFAUT;

        x -= this.x;
        y -= this.y;

        codeRetour = fermeture(x, y) == CODE_RETOUR_DEFAUT ? CODE_RETOUR_DEFAUT : fermeture(x, y);
        return codeRetour;
    }

    private int fermeture(int x, int y) {
        int codeRetour = CODE_RETOUR_DEFAUT;
        if (CROIX_X < x && x < CROIX_X_FIN && CROIX_Y < y && y < CROIX_Y_FIN) {
            setShow(false);
            codeRetour = CODE_RETOUR_FERMETURE;
        }
        return codeRetour;
    }
}
