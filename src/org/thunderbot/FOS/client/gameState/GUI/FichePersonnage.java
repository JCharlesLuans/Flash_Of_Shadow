package org.thunderbot.FOS.client.gameState.GUI;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class FichePersonnage {

    private static final String TITRE = "Personnage";

    private boolean active;

    private Image imageFond;

    private int centreX;
    private int centreY;

    private int millieuGameContainerX;
    private int millieuGameContainerY;

    public FichePersonnage(GameContainer gameContainer) throws SlickException {
        // Init de l'image de fond
        imageFond = new Image("res/menuState/gui/personnage.png");

        // Recuperation du centre de l'ecran
        millieuGameContainerX = gameContainer.getWidth() / 2;
        millieuGameContainerY = gameContainer.getHeight() / 2;

        active = false;
    }

    public void render(Graphics graphics) {
        if (active) {
            graphics.drawString(TITRE, centreX - graphics.getFont().getWidth(TITRE) / 2, imageFond.getHeight() / 2);
            graphics.drawImage(imageFond, centreX - imageFond.getWidth() / 2, centreY - imageFond.getHeight() / 2);
        }
    }

    public void setCentreX(int centreX) {
        this.centreX = centreX;
    }

    public void setCentreY(int centreY) {
        this.centreY = centreY;
    }
}
