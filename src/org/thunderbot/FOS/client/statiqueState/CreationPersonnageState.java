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
import org.thunderbot.FOS.database.beans.Faction;

import java.util.ArrayList;

public class CreationPersonnageState extends BasicGameState {

    public static final int ID = 3;

    private static final int ZT_NOM_WIDTH = 480;
    private static final int ZT_NOM_HEIGHT = 40;
    private static final int ZT_NOM_DELTA_HEIGHT = 120;

    private static final int BTN_VALIDER_LONGUEUR = 100;
    private static final int BTN_VALIDER_HAUTEUR  = 50;
    private static final int BTN_VALIDER_DELTA = 65;

    private static final int BTN_CLASSE_LONGUEUR = 75;
    private static final int BTN_CLASSE_HAUTEUR = 50;
    private static final int BTN_CLASSE_X_START = 100;
    private static final int BTN_CLASSE_Y_START = 50;

    private Client client;

    private Image background;

    private Bouton btnValider;
    private TextField ztNom;

    private ArrayList<Bouton> listBtnClasse;
    private ArrayList<Bouton> listBtnFaction;



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

        listBtnClasse = new ArrayList<>();
        listBtnFaction = new ArrayList<>();

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
            listBtnClasse.add(new Bouton(BTN_CLASSE_X_START * (i + 1), BTN_CLASSE_Y_START, BTN_CLASSE_LONGUEUR, BTN_VALIDER_HAUTEUR, listeClasse.get(i).getNom()));
        }

        // TODO chercher la liste des faction sur la BD du serveur
        ArrayList<Faction> listeFaction = client.chargementListeFaction();
        for (int i = 0; i < listeFaction.size(); i++) {
            listBtnFaction.add(new Bouton(BTN_CLASSE_X_START * (i + 1), BTN_CLASSE_Y_START + BTN_CLASSE_HAUTEUR + 50, BTN_CLASSE_LONGUEUR, BTN_VALIDER_HAUTEUR, listeFaction.get(i).getNom()));
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        background.draw(0, 0, gameContainer.getWidth(), gameContainer.getHeight());

        btnValider.render(graphics);
        ztNom.render(gameContainer, graphics);

        for (int i = 0; i < listBtnClasse.size(); i++) {
            listBtnClasse.get(i).render(graphics);
        }

        for (int i = 0; i < listBtnFaction.size(); i++) {
            listBtnFaction.get(i).render(graphics);
        }
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
