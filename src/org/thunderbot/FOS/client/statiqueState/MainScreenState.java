/*
 * MainScreenState.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.statiqueState;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.thunderbot.FOS.client.network.Client;
import org.thunderbot.FOS.client.gameState.MapGameState;
import org.newdawn.slick.gui.TextField;

import java.io.IOException;

/**
 * Menu principal
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class MainScreenState extends BasicGameState {

    public static final int ID = 1;
    private Image background;
    private StateBasedGame game;

    private TextField zoneSaisie;

    private Client client;

    public MainScreenState(Client client) {
        this.client = client;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.game = stateBasedGame;
        background = new Image("res/menuState/background.png");

        //L'initialisation du TextField
        zoneSaisie = new TextField(gameContainer, gameContainer.getDefaultFont(), 110, 30, 480, 40);

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        background.draw(0, 0, container.getWidth(), container.getHeight());
        g.drawString("Appuyer sur une touche", 300, 300);
        zoneSaisie.render(container, g);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void keyReleased(int key, char c) {
        if (key == Input.KEY_ENTER) {
            try {
                connexionJeu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Entrée dans la map game state et authentification au serveur multijoueur
     */
    private void connexionJeu() throws IOException {
        String pseudo = zoneSaisie.getText();
        client.authentification(pseudo);
        client.setPseudo(pseudo);
        game.enterState(MapGameState.ID);
    }
}
