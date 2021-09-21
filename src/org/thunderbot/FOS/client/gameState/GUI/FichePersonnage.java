package org.thunderbot.FOS.client.gameState.GUI;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.thunderbot.FOS.client.network.Client;

public class FichePersonnage extends FenetreEnJeu{

    private static final String TITRE = "Personnage";

    private static final int X_FERMER = 375;
    private static final int Y_FERMER = 5;
    private static final int LONGUEUR_FERMER = 30;
    private static final int HAUTEUR_FERMER = 25;

    public FichePersonnage(Gui gui, Client client, GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super(gui, client, gameContainer, stateBasedGame);

        // Init de l'image de fond
        imgFond = new Image("res/menuState/gui/personnage.png");
        initPosition(gameContainer);
    }

    public void render(Graphics graphics) {
        if (active) {
            graphics.drawString(TITRE, centreX - graphics.getFont().getWidth(TITRE) / 2, imgFond.getHeight() / 2);
            graphics.drawImage(imgFond, centreX - imgFond.getWidth() / 2, centreY - imgFond.getHeight() / 2);
        }
    }

    public void setCentreX(int centreX) {
        this.centreX = centreX;
    }

    public void setCentreY(int centreY) {
        this.centreY = centreY;
    }

    @Override
    public void mouseMouved(int y, int x) {

    }

    @Override
    public void mouseClicked(int x, int y) {
        x -= this.x;
        y -= this.y;
        if (active)
            fermer(x, y, X_FERMER, Y_FERMER, LONGUEUR_FERMER, HAUTEUR_FERMER);
    }
}
