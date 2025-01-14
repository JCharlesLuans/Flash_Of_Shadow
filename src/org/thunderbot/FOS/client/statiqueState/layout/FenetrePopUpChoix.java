package org.thunderbot.FOS.client.statiqueState.layout;

import org.newdawn.slick.SlickException;

public class FenetrePopUpChoix extends FenetrePopUp{

    private final static int TAILLE_POLICE = 35;

    private final static int OUI_X = 250;
    private final static int OUI_X_FIN = 310;
    private final static int OUI_Y = 365;
    private final static int OUI_Y_FIN = 400;

    private final static int NON_X = 350;
    private final static int NON_X_FIN = 415;
    private final static int NON_Y = 365;
    private final static int NON_Y_FIN = 400;

    private boolean oui;
    private boolean non;

    public FenetrePopUpChoix(String message) throws SlickException {
        super(message, "res/menuState/gui/fenetrePopUpChoix.png");
        getFont().setSize(TAILLE_POLICE);
    }


    public int mouseClicked(int button, int x, int y, int clickCount) {

        x -= super.getX();
        y -= super.getY();

        clickOui(x, y);
        clickNon(x, y);

        x += super.getX();
        y += super.getY();
        return super.mouseClicked(button, x, y, clickCount);
    }

    public boolean clickOui(int x, int y) {
        if (OUI_X < x && x < OUI_X_FIN && OUI_Y < y && y < OUI_Y_FIN) {
            setShow(false);
            oui = true;
            non = false;
        }
        return oui;
    }

    private boolean clickNon(int x, int y) {

        if (NON_X < x && x < NON_X_FIN && NON_Y < y && y < NON_Y_FIN) {
            setShow(false);
            oui = false;
            non = true;
        }
        return non;
    }

    public boolean isOui() {
        return oui;
    }

    public boolean isNon() {
        return non;
    }

}
