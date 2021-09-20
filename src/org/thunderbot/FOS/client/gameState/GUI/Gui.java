package org.thunderbot.FOS.client.gameState.GUI;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Gui {

    private Menu menu;

    private int x;
    private int y;

    public Gui(GameContainer gameContainer) throws SlickException {
        menu = new Menu(gameContainer);
    }

    public Menu getMenu() {
        return menu;
    }

    public void render(Graphics graphics) {
        menu.render(graphics);
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
    }

    public void setY(int y) {
        this.y = y;
        menu.setCentreY(y);
    }
}
