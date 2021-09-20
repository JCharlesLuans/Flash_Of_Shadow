package org.thunderbot.FOS.client.gameState.GUI;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Menu qui permet au joueur les action suivante :
 * Ouvrir son inventaire, ouvrir sa fiche de personnage, ouvrir la carte monde, ouvrir les compétances, ouvrir les quetes
 * ouvrir la page de la guilde, les options, et se deconnecter
 */
public class Menu {

    private Image imgFond;

    private boolean active;

    int x;
    int y;

    public Menu(GameContainer gameContainer) throws SlickException {
        imgFond = new Image("res/menuState/gui/munu.png");
        x = gameContainer.getWidth() / 2 - imgFond.getWidth() / 2;
        y = gameContainer.getHeight() / 2 - imgFond.getHeight() / 2;
    }

    public void render(Graphics graphics) {
        if (active) {
            graphics.drawImage(imgFond, x, y);
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
