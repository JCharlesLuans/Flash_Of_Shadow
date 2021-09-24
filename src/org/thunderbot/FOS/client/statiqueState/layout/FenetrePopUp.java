/*
 * FenetrePopUp.java                 19/09/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.statiqueState.layout;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.thunderbot.FOS.client.ChaosRevolt;
import org.thunderbot.FOS.client.statiqueState.police.MedievalSharp;

/**
 * Affiche un message d'avertissement
 *
 * @author Jean-Charles Luans
 */
public class FenetrePopUp {

    public static final int CODE_RETOUR_DEFAUT = -1;
    public static final int CODE_RETOUR_FERMETURE = 0;
    public static final int CODE_RETOUR_OK = 1;

    private static final int OK_X = 305;
    private static final int OK_X_FIN = 665;
    private static final int OK_Y = 370;
    private static final int OK_Y_FIN = 400;

    private static final int CROIX_X = 597;
    private static final int CROIX_X_FIN = 624;
    private static final int CROIX_Y = 14;
    private static final int CROIX_Y_FIN = 36;

    private static final int DELTA_START_MESSAGE = 50;

    private static final int TAILLE_POLICE = 55;

    private MedievalSharp font;

    private int y;
    private int x;

    private String message;

    private boolean show;

    private Image imageFond;

    private Sound son;

    public FenetrePopUp(String message) throws SlickException {
        imageFond = new Image("res/menuState/gui/fenetrePopUp.png");
        initFenetre(message);
    }

    public FenetrePopUp(String message, String srcImage) throws SlickException {
        imageFond = new Image(srcImage);
        initFenetre(message);
    }

    public void render(Graphics graphics) {
        if (show) {
            graphics.setFont(font.getFont());
            graphics.drawImage(imageFond, x, y);
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

        codeRetour = clickFermeture(x, y);
        codeRetour = codeRetour != CODE_RETOUR_DEFAUT ? codeRetour : clickOK(x, y);
        return codeRetour;
    }

    private int clickFermeture(int x, int y) {
        int codeRetour = CODE_RETOUR_DEFAUT;
        if (CROIX_X < x && x < CROIX_X_FIN && CROIX_Y < y && y < CROIX_Y_FIN) {
            setShow(false);
            codeRetour = CODE_RETOUR_FERMETURE;
        }
        return codeRetour;
    }

    private int clickOK(int x, int y) {
        int codeRetour = CODE_RETOUR_DEFAUT;
        if (OK_X < x && x < OK_X_FIN && OK_Y < y && y < OK_Y_FIN) {
            setShow(false);
            codeRetour = CODE_RETOUR_OK;
        }
        return codeRetour;
    }

    private void initFenetre(String message) throws SlickException {
        son = new Sound("res/menuState/son/ouvertureFenetre.wav");

        this.message = message;
        this.font = new MedievalSharp(TAILLE_POLICE);
        show = false;

        x = ChaosRevolt.WIDTH / 2 - imageFond.getWidth() / 2;
        y = ChaosRevolt.HEIGHT / 2 - imageFond.getHeight() / 2;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    protected MedievalSharp getFont() {
        return font;
    }
}
