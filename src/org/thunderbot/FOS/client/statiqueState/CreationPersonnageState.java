/*
 * CreationPersonnageState.java             15/09/2021
 * Copyright et copyleft TNLag Corp.
 */
package org.thunderbot.FOS.client.statiqueState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.thunderbot.FOS.client.network.Client;
import org.thunderbot.FOS.client.statiqueState.layout.Bouton;

public class CreationPersonnageState extends BasicGameState {

    public static final int ID = 3;

    private Client client;

    private Image background;

    private TextField zoneSaisieNom;

    private Bouton btnValider;



    public CreationPersonnageState(Client client) {
        this.client = client;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

        background = new Image("res/menuState/backgroundCreationPerso.jpg");

        //L'initialisation du TextField
        zoneSaisieNom = new TextField(gameContainer, gameContainer.getDefaultFont(), 110, 30, 480, 40);
        btnValider = new Bouton(100, 50, gameContainer.getWidth() - 150, gameContainer.getHeight() - 50, "Valider");

        // TODO chercher la liste des classe sur la BD du serveur

        // TODO chercher la liste des faction sur la BD du serveur

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        background.draw(0, 0, gameContainer.getWidth(), gameContainer.getHeight());
        zoneSaisieNom.render(gameContainer, graphics);
        btnValider.render(graphics);

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }
}
