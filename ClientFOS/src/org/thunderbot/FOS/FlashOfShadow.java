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

/**
 * Jeu
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class FlashOfShadow extends StateBasedGame {

    /**
     * Lancement du jeu
     */
    public static void main(String[] args) throws SlickException {
        new AppGameContainer(new FlashOfShadow(), 1240, 720, false).start();
    }

    public FlashOfShadow() {
        super("Flash Of Shadow");
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        addState(new MainScreenState());
        addState(new MapGameState());
    }
}
