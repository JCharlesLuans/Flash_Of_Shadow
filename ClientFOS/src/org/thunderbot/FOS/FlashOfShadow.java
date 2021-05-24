/*
 * FlashOfShadow.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.thunderbot.FOS.gameState.MapGameState;
import org.thunderbot.FOS.statiqueState.MainScreenState;

import java.io.IOException;

/**
 * Jeu
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class FlashOfShadow extends StateBasedGame {

    private Client client;

    /**
     * Lancement du jeu
     */
    public static void main(String[] args) throws SlickException, IOException {
        new AppGameContainer(new FlashOfShadow(), 1240, 720, false).start();
    }

    public FlashOfShadow() throws IOException {
        super("Flash Of Shadow");
        client = new Client();
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        addState(new MainScreenState(client));
        addState(new MapGameState(client));
    }

    @Override
    public boolean closeRequested() {
        try {
            client.deconnexion();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0); // Use this if you want to quit the app.
        return false;
    }
}
