/*
 * MapGameState.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.gameState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.thunderbot.FOS.client.gameState.entite.Personnage;
import org.thunderbot.FOS.client.gameState.world.Map;
import org.thunderbot.FOS.client.network.Client;

/**
 * State du monde de base
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class MapGameState extends BasicGameState {

    /** Carte afficher a l'écran */
    Map map;

    /** Client pour la communication multijoueur */
    Client client;

    Personnage joueur;

    public static final int ID = 2;
    // déclaration des autres objets (cf leçon précédente)

    public MapGameState(Client client) {
        this.client = client;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        map = new Map();
        joueur = new Personnage();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        map.renderBackground();

        // Render des joueur et des mobs
        joueur.render(graphics);

        map.renderForeground();
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        // mise à jour (cf leçon précédente)
    }

    @Override
    public void keyReleased(int key, char c) {

    }

    @Override
    public int getID() {
        return ID;
    }
}
