package org.thunderbot.FOS.client.gameState.GUI;

import org.newdawn.slick.*;
import org.thunderbot.FOS.client.ChaosRevolt;

/**
 * Menu qui permet au joueur les action suivante :
 * Ouvrir son inventaire, ouvrir sa fiche de personnage, ouvrir la carte monde, ouvrir les compétances, ouvrir les quetes
 * ouvrir la page de la guilde, les options, et se deconnecter
 */
public class Menu {

    private static final int Y_INVENTAIRE = 195;
    private static final int Y_PERSONNAGE = 235;
    private static final int Y_CARTE = 275;
    private static final int Y_COMPETANCE = 314;
    private static final int Y_QUETE = 362;
    private static final int Y_GUILDE = 405;
    private static final int Y_OPTION = 445;
    private static final int Y_DECONNEXION= 485;

    private static final int INVENTAIRE = 1;
    private static final int PERSONNAGE = 2;
    private static final int CARTE = 3;
    private static final int COMPETANCE = 4;
    private static final int QUETE = 5;
    private static final int GUILDE = 6;
    private static final int OPTION = 7;
    private static final int DECONNEXION= 8;


    private static final int MIN_X = 65;
    private static final int MAX_X = 325;

    private static final int MAX_Y = 515;

    private static final int DELTA_LIGNE = 30;

    private static final int X_BARRE = 115;
    private static final int LONGUEUR_BARRE = 75;
    private static final int HAUTEUR_BARRE = 1;

    private Image imgFond;

    private boolean active;

    private Sound sonPage;
    private Sound sonClick;

    private int x;
    private int y;

    private int centreX;
    private int centreY;

    private boolean barreVisible;
    private int positionYBarre;

    private int code;

    public Menu(GameContainer gameContainer) throws SlickException {
        imgFond = new Image("res/menuState/gui/menu.png");
        sonPage = new Sound("res/menuState/son/ouvertureFenetre.wav");
        x = gameContainer.getWidth() / 2 - imgFond.getWidth() / 2;
        y = gameContainer.getHeight() / 2 - imgFond.getHeight() / 2;
        positionYBarre = 0;
    }

    public void render(Graphics graphics) {
        if (active) {
            graphics.drawImage(imgFond, centreX - imgFond.getWidth() / 2, centreY - imgFond.getHeight() / 2);
            if (barreVisible) {
                graphics.drawRect(X_BARRE + x, positionYBarre + centreY - imgFond.getHeight() / 2 + DELTA_LIGNE, LONGUEUR_BARRE, HAUTEUR_BARRE);
            }
        }
    }

    public void setActive(boolean active) {
        this.active = active;
        sonPage.play();
    }

    public boolean isActive() {
        return active;
    }

    public void mouseClicked() {
        switch (code) {
            case INVENTAIRE:
                System.out.println("Inventaire");
                break;
            case PERSONNAGE:
                System.out.println("Personnage");
                break;
            case CARTE:
                System.out.println("Carte");
                break;
            case COMPETANCE:
                System.out.println("Compétance");
                break;
            case QUETE:
                System.out.println("Quetes");
                break;
            case GUILDE:
                System.out.println("Guilde");
                break;
            case OPTION:
                System.out.println("Option");
                break;
            case DECONNEXION:
                System.out.println("Deconnexion");
                break;
        }
    }

    public void setCentreX(int centreX) {
        this.centreX = centreX;
    }

    public void setCentreY(int centreY) {
        this.centreY = centreY;
    }

    public void mouseMouved(int y, int x) {
        x -= this.x;
        y -= this.y;

        barreVisible = true;

        // En dehors du menu
        if (MIN_X > x || MAX_X < x || Y_INVENTAIRE > y || MAX_Y < y) {
            barreVisible = false;
        } else if (y < Y_PERSONNAGE) {
            positionYBarre = Y_INVENTAIRE;
            code = INVENTAIRE;
        } else if (y < Y_CARTE) {
            positionYBarre = Y_PERSONNAGE;
            code = PERSONNAGE;
        } else if (y < Y_COMPETANCE) {
            positionYBarre = Y_CARTE;
            code = CARTE;
        } else if (y < Y_QUETE) {
            positionYBarre = Y_COMPETANCE;
            code = COMPETANCE;
        } else if (y < Y_GUILDE) {
            positionYBarre = Y_QUETE;
            code = QUETE;
        } else if (y < Y_OPTION) {
            positionYBarre = Y_GUILDE;
            code = GUILDE;
        } else if (y < Y_DECONNEXION) {
            positionYBarre = Y_OPTION;
            code = OPTION;
        } else {
            positionYBarre = Y_DECONNEXION;
            code = DECONNEXION;
        }
    }
}
