package org.thunderbot.FOS.client.gameState.GUI;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.thunderbot.FOS.client.network.Client;

public class Gui {

    private Menu menu;
    private FichePersonnage fichePersonnage;

    private int x;
    private int y;

    public Gui(GameContainer gameContainer, Client client, StateBasedGame stateBasedGame) throws SlickException {
        menu = new Menu(gameContainer, client, stateBasedGame);
        fichePersonnage = new FichePersonnage(gameContainer);
    }

    public Menu getMenu() {
        return menu;
    }

    public void render(Graphics graphics) {
        menu.render(graphics);
        fichePersonnage.render(graphics);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
        menu.setCentreX(x);
        fichePersonnage.setCentreX(x);
    }

    public void setY(int y) {
        this.y = y;
        menu.setCentreY(y);
        fichePersonnage.setCentreY(y);
    }
}
