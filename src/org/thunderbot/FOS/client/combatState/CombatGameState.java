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
import org.thunderbot.FOS.client.gameState.entite.Personnage;
import org.thunderbot.FOS.client.gameState.entite.PersonnageJoueurClient;
import org.thunderbot.FOS.client.gameState.entite.PersonnageNonJoueur;
import org.thunderbot.FOS.client.gameState.phisique.CombatController;
import org.thunderbot.FOS.client.gameState.world.Carte;
import org.thunderbot.FOS.client.network.Client;
import org.thunderbot.FOS.database.beans.PNJ;

import java.util.ArrayList;

/**
 * State des combats
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class CombatGameState extends BasicGameState {

    public static final int ID = 4;

    public static final int TAILLE_CASE = 64;
    public static final int TAILLE_INTERFACE = 2;

    private Image background;

    private InterfaceJoueur interfaceJoueur;
    private Client client;

    private CombatController combatController;

    private Carte carte;

    private Case[][] terrain;

    int nombreCaseHauteur;

    /** Entité en combats */
    private ArrayList<PersonnageNonJoueur> listePNJ;
    private PersonnageJoueurClient personnage;

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
        combatController = new CombatController(this);
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        gameContainer.getInput().removeAllKeyListeners();
        gameContainer.getInput().removeAllControllerListeners();
        gameContainer.getInput().removeAllMouseListeners();

        gameContainer.getInput().addControllerListener(combatController);
        gameContainer.getInput().addMouseListener(combatController);
        gameContainer.getInput().addKeyListener(combatController);

        System.out.println(gameContainer.getInput().toString());
        initCase();
        initPNJ();
        initJoueur();
        initInterface();
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        background.draw(0, 0, gameContainer.getWidth(), gameContainer.getHeight());
        renderGrille(graphics);

        renderPNJ(graphics);
        personnage.render(graphics);

        interfaceJoueur.render(gameContainer, graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    /**
     * Genere tout les cases de se terrain
     */
    private void initCase() {
        int nombreCaseLongueur = ChaosRevolt.WIDTH / TAILLE_CASE;
        nombreCaseHauteur  = ChaosRevolt.HEIGHT / TAILLE_CASE - TAILLE_INTERFACE;
        terrain = new Case[nombreCaseHauteur][nombreCaseLongueur];

        for (int i = 0; i < nombreCaseHauteur; i++) {
            for (int j = 0; j < nombreCaseLongueur; j++) {
                terrain[i][j] = new Case((1 +1) * j, TAILLE_CASE*j, TAILLE_CASE*i, TAILLE_CASE);
            }
        }
    }

    /**
     * Génére aléatoirement les PNJ depuis le serveurs, ainsi que les informations qui leurs sont relatives
     */
    private void initPNJ() {
        try {
            // RAZ de la liste des PNJ
            listePNJ = new ArrayList<>();

            ArrayList<PNJ> listeTmp;

            listeTmp = client.initialiseCombatPNJ();

            System.out.println(listeTmp);

            for (PNJ pnjTmp : listeTmp) {
                listePNJ.add(new PersonnageNonJoueur(pnjTmp));
            }
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Génére le joueur et lui attribut une position aléatoire
     */
    private void initJoueur() {
        try {
            personnage = new PersonnageJoueurClient(client.initialiseCombatJoueur());
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialise l'interface de combat
     */
    private void initInterface() throws SlickException {
        interfaceJoueur = new InterfaceJoueur(nombreCaseHauteur);
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

    private void renderPNJ(Graphics graphics) {
        for (PersonnageNonJoueur pnj : listePNJ) {
            pnj.render(graphics);
        }
    }
}
