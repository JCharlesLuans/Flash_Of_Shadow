/*
 * FenetrePopUp.java                 19/09/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.statiqueState.layout;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;

/**
 * Affiche un message d'avertissement
 *
 * @author Jean-Charles Luans
 */
public class FenetrePopUp {

    public static final int CODE_RETOUR_DEFAUT = -1;
    public static final int CODE_RETOUR_FERMETURE = 0;
    public static final int CODE_RETOUR_OK = 1;
    public static final int CODE_RETOUR_NON = 2;

    private static final int OK_X = 305;
    private static final int OK_X_FIN = 665;
    private static final int OK_Y = 370;
    private static final int OK_Y_FIN = 400;

    private static final int CROIX_X = 597;
    private static final int CROIX_X_FIN = 624;
    private static final int CROIX_Y = 14;
    private static final int CROIX_Y_FIN = 36;

    private static final int DELTA_START_MESSAGE = 50;



    private int y;
    private int x;

    private String message;

    private boolean show;

    private Image imageFond;

    private Sound son;

    private UnicodeFont font;

    public FenetrePopUp(GameContainer gameContainer, String message, int fontSize) throws SlickException {
        imageFond = new Image("res/menuState/gui/fenetrePopUp.png");
        initFenetre(gameContainer, message, fontSize);
    }

    public FenetrePopUp(GameContainer gameContainer, String message, String srcImage, int fontSize) throws SlickException {
        imageFond = new Image(srcImage);
        initFenetre(gameContainer, message, fontSize);
    }

    public void render(Graphics graphics) {
        graphics.setFont(font);
        if (show) {
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

    private void initFenetre(GameContainer gameContainer, String message, int fontSize) throws SlickException {
        son = new Sound("res/menuState/son/ouvertureFenetre.wav");

        this.message = message;
        show = false;

        x = gameContainer.getWidth() / 2 - imageFond.getWidth() / 2;
        y = gameContainer.getHeight() / 2 - imageFond.getHeight() / 2;

        font = new UnicodeFont("res/menuState/gui/police/MedievalSharp-Regular.ttf", fontSize, false, false);
        font.addAsciiGlyphs();
        font.addGlyphs(400,600);
        font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        font.loadGlyphs();
    }

    public UnicodeFont getFont() {
        return font;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }
}
