/*
 * CombatGameState.java             24/09/2021
 * Copyright et copyleft TNLag Corp.
 */
package org.thunderbot.FOS.client.combatState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.thunderbot.FOS.client.ChaosRevolt;
import org.thunderbot.FOS.client.gameState.world.Carte;
import org.thunderbot.FOS.client.network.Client;

/**
 * State du monde de base
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class CombatGameState extends BasicGameState {

    public static final int ID = 4;

    public static final int TAILLE_CASE = 64;

    private Image background;

    private Client client;

    private Carte carte;

    private Case[][] terrain;

    public CombatGameState(Client client) throws SlickException {
        background = new Image("res/combatState/fond.png");
        this.client = client;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        initCase();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        background.draw(0, 0, gameContainer.getWidth(), gameContainer.getHeight());
        renderGrille(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    /**
     * Genere tout les cases de se terrain
     */
    private void initCase() {
        int nombreCaseLongueur = ChaosRevolt.WIDTH / TAILLE_CASE;
        int nombreCaseHauteur  = ChaosRevolt.HEIGHT / TAILLE_CASE;
        terrain = new Case[nombreCaseHauteur][nombreCaseLongueur];

        for (int i = 0; i < nombreCaseHauteur; i++) {
            for (int j = 0; j < nombreCaseLongueur; j++) {
                terrain[i][j] = new Case((1 +1) * j, TAILLE_CASE*j, TAILLE_CASE*i, TAILLE_CASE);
            }
        }
    }

    /**
     * Affichage les cases du terrain
     */
    private void renderGrille(Graphics graphics) {
        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[i].length; j++) {
                terrain[i][j].render(graphics);
            }
        }
    }
}
