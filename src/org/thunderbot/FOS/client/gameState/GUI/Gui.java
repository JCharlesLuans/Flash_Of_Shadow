package org.thunderbot.FOS.client.gameState.GUI;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Gui {

    private Menu menu;

    public Gui(GameContainer gameContainer) throws SlickException {
        menu = new Menu(gameContainer);
    }

    public Menu getMenu() {
        return menu;
    }

    public void render(Graphics graphics) {
        menu.render(graphics);
    }
}
