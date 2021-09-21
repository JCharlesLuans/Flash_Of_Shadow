package org.thunderbot.FOS.client.gameState.GUI;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.thunderbot.FOS.client.network.Client;
import org.thunderbot.FOS.client.statiqueState.police.MedievalSharp;

public abstract class FenetreEnJeu {

    /** Immage du menu */
    protected Image imgFond;

    /** Son qui s'effectue lorsque la page est ouverte */
    protected Sound sonPage;

    /** Son qui s'effectue ors de click */
    protected Sound sonClick;

    /** Position en X*/
    protected int x;

    /** Position en X*/
    protected int y;

    /**  Client qui permet de communiquer avec le serveur */
    protected Client client;
    protected StateBasedGame stateBasedGame;

    /** GUI qui englobe cette fenetre en jeu */
    protected Gui gui;

    /** Indique si la fenetre est active ou non */
    protected boolean active;

    /** Position au centre de l'ecran */
    protected int centreX;
    protected int centreY;

    /** Police pour afficher les string */
    protected MedievalSharp font;

    public FenetreEnJeu(Gui gui, Client client, GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

        font = new MedievalSharp(35);

        sonPage = new Sound("res/menuState/son/ouvertureFenetre.wav");
        sonClick = new Sound("res/menuState/son/click.wav");

        active = false;

        this.client = client;
        this.stateBasedGame = stateBasedGame;
        this.gui = gui;
    }

    public abstract void render(Graphics graphics);

    public void ouvrir() {
        setActive(true);
    }

    private void setActive(boolean active) {
        this.active = active;
        sonPage.play();
    }

    public boolean isActive() {
        return active;
    }

    public abstract void mouseClicked(int x, int y);

    public void setCentreX(int centreX) {
        this.centreX = centreX;
    }

    public void setCentreY(int centreY) {
        this.centreY = centreY;
    }

    public abstract void mouseMouved(int y, int x);

    protected void initPosition(GameContainer gameContainer) {
        x = gameContainer.getWidth() / 2 - imgFond.getWidth() / 2;
        y = gameContainer.getHeight() / 2 - imgFond.getHeight() / 2;
    }

    protected void fermer(int x, int y, int positionBoutonX, int positionBoutonY, int longueurBouton, int hauteurBouton) {
        if (positionBoutonX < x && x < positionBoutonX + longueurBouton
            && positionBoutonY < y && y < positionBoutonY + hauteurBouton) {
            setActive(false);
        }
    }

    public void fermer() {
        setActive(false);
    }
}
