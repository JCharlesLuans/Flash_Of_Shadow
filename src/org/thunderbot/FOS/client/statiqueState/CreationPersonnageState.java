/*
 * CreationPersonnageState.java             15/09/2021
 * Copyright et copyleft TNLag Corp.
 */
package org.thunderbot.FOS.client.statiqueState;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.thunderbot.FOS.client.network.Client;
import org.thunderbot.FOS.client.statiqueState.layout.Bouton;
import org.thunderbot.FOS.database.beans.Classe;

import java.util.ArrayList;

public class CreationPersonnageState extends BasicGameState {

    public static final int ID = 3;

    private static final int ZT_NOM_WIDTH = 480;
    private static final int ZT_NOM_HEIGHT = 40;
    private static final int ZT_NOM_DELTA_HEIGHT = 120;

    private static final int BTN_VALIDER_LONGUEUR = 100;
    private static final int BTN_VALIDER_HAUTEUR  = 50;
    private static final int BTN_VALIDER_DELTA = 65;

    private Client client;

    private Image background;

    private Bouton btnValider;
    private TextField ztNom;



    public CreationPersonnageState(Client client) {
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

        background = new Image("res/menuState/backgroundCreationPerso.jpg");

        btnValider = new Bouton(gameContainer.getWidth() / 2 - BTN_VALIDER_LONGUEUR / 2,
                gameContainer.getHeight() - BTN_VALIDER_DELTA,
                BTN_VALIDER_LONGUEUR, BTN_VALIDER_HAUTEUR, "Valider");

        ztNom = new TextField(gameContainer, gameContainer.getDefaultFont(),
                gameContainer.getWidth() / 2 - ZT_NOM_WIDTH / 2,
                gameContainer.getHeight() - ZT_NOM_DELTA_HEIGHT,
                ZT_NOM_WIDTH, ZT_NOM_HEIGHT);

        // TODO chercher la liste des classe sur la BD du serveur
        ArrayList<Classe> listeClasse = client.chargementListeClasse();
        for (int i = 0; i < listeClasse.size(); i++) {
            System.out.println(listeClasse.get(i).toString());
        }

        // TODO chercher la liste des faction sur la BD du serveur

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        background.draw(0, 0, gameContainer.getWidth(), gameContainer.getHeight());

        btnValider.render(graphics);
        ztNom.render(gameContainer, graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        if (btnValider.isInBouton(x, y)) {

        }
    }

}
