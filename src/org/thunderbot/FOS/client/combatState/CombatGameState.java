/*
 * CombatGameState.java             24/09/2021
 * Copyright et copyleft TNLag Corp.
 */
package org.thunderbot.FOS.client.combatState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.thunderbot.FOS.client.network.Client;

/**
 * State du monde de base
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class CombatGameState extends BasicGameState {

    public static final int ID = 4;

    public CombatGameState(Client client) {
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }
}
