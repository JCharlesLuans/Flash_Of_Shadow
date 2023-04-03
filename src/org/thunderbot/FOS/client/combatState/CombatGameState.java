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
import org.thunderbot.FOS.client.gameState.MapGameState;
import org.thunderbot.FOS.client.gameState.entite.PersonnageJoueurClient;
import org.thunderbot.FOS.client.gameState.entite.PersonnageNonJoueur;
import org.thunderbot.FOS.client.gameState.phisique.CombatController;
import org.thunderbot.FOS.client.gameState.phisique.Stats;
import org.thunderbot.FOS.client.gameState.world.Carte;
import org.thunderbot.FOS.client.gameState.world.Terrain;
import org.thunderbot.FOS.client.network.Client;
import org.thunderbot.FOS.database.beans.PNJ;
import org.thunderbot.FOS.database.beans.Personnage;

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
    public static final int TAILLE_INTERFACE = 3;

    private Image background;

    private InterfaceJoueur interfaceJoueur;
    private Client client;

    private CombatController combatController;

    private Carte carte;

    private Terrain terrain;

    private StateBasedGame stateBasedGame;

    int nombreCaseHauteur;

    /** Entité en combats */
    private ArrayList<PersonnageNonJoueur> listePNJAAfficher;
    private PersonnageJoueurClient personnageAAfficher;

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

        this.stateBasedGame = stateBasedGame;

        terrain = new Terrain();

        initPNJ();
        initJoueur();
        initInterface(gameContainer);
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        background.draw(0, 0, gameContainer.getWidth(), gameContainer.getHeight());
        terrain.render(graphics);

        renderPNJ(graphics);
        personnageAAfficher.render(graphics);

        interfaceJoueur.render(gameContainer, graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        interfaceJoueur.update(personnageAAfficher);
    }



    /**
     * Génére aléatoirement les PNJ depuis le serveurs, ainsi que les informations qui leurs sont relatives
     */
    private void initPNJ() {
        try {
            // RAZ de la liste des PNJ
            listePNJAAfficher = new ArrayList<>();

            ArrayList<PNJ> listeTmp;

            listeTmp = client.initialiseCombatPNJ();

            System.out.println(listeTmp);

            for (PNJ pnjTmp : listeTmp) {
                listePNJAAfficher.add(new PersonnageNonJoueur(pnjTmp));
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
            personnageAAfficher = new PersonnageJoueurClient(client.initialiseCombatJoueur());
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialise l'interface de combat
     */
    private void initInterface(GameContainer gameContainer) throws SlickException {
        interfaceJoueur = new InterfaceJoueur(gameContainer, client.getPersonnage().getListeCompetence(), terrain.getNombreCaseHauteur());
    }

    private void renderPNJ(Graphics graphics) {
        for (PersonnageNonJoueur pnj : listePNJAAfficher) {
            pnj.render(graphics);
        }
    }

    public InterfaceJoueur getInterfaceJoueur() {
        return interfaceJoueur;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public PersonnageJoueurClient getPersonnageAAfficher() {
        return personnageAAfficher;
    }

    public void miseAJour() {
        try {
            Personnage personnageData = new Personnage(this.personnageAAfficher);
            ArrayList<PNJ> listeDataPNJ = new ArrayList<>();

            for (PersonnageNonJoueur personnageNonJoueur : listePNJAAfficher) {
                listeDataPNJ.add(new PNJ(personnageNonJoueur));
            }

            // Mise a jour des données sur le serveur puis remise a jours des attributs des paramettres
            client.updateServeurCombat(personnageData, listeDataPNJ);

            listePNJAAfficher = new ArrayList<>();

            for (PNJ pnj : listeDataPNJ) {
                listePNJAAfficher.add(new PersonnageNonJoueur(pnj));
            }
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
    }

    public void fin() {
        if (personnageAAfficher.getStats().getVieRestante() == 0 || listePNJAAfficher.size() == 0) {
            Personnage personnageData = new Personnage(this.personnageAAfficher);
            ArrayList<PNJ> listeDataPNJ = new ArrayList<>();

            for (PersonnageNonJoueur personnageNonJoueur : listePNJAAfficher) {
                listeDataPNJ.add(new PNJ(personnageNonJoueur));
            }

            // Mise a jour des données sur le serveur puis remise a jours des attributs des paramettres
            client.finServeurCombat(personnageData, listeDataPNJ);

            stateBasedGame.enterState(MapGameState.ID);
        }
    }

    public void deplacement(int id) {

        float futurX, futurY;
        float x, y;
        float resteX, resteY;

        boolean possible;

        // Verification que le joueur a bien clicker sur le terrain
        if (id > 0) {

            // Transformation de l'id en coordonner
            futurX = terrain.getXById(id);
            futurY = terrain.getYById(id);

            // Convertion des coordonner du joueur en coordonner de combats
            x = (personnageAAfficher.getPositionX() + 32) / TAILLE_CASE;
            y = (personnageAAfficher.getPositionY() + 16) / TAILLE_CASE;

            System.out.println("X = " + x);
            System.out.println("Y = " + y);
            System.out.println("futurX = " + futurX);
            System.out.println("futurY = " + futurY);

            // Verification si coordonnée > stats.mouvement
            // On va vers la GAUCHE
            if (x > futurX) {
                resteX = x - futurX;
            } else {
                resteX = futurX - x;
            }

            if (y > futurY) {
                resteY = y - futurY;
            } else {
                resteY = futurY - y;
            }

            if (resteX + resteY <= personnageAAfficher.getStats().getMouvementsRestants()) {
                // Déplacement du joueur sur la case ou le joueur a cliquer
                personnageAAfficher.setPositionX(terrain.getXById(id) * CombatGameState.TAILLE_CASE - 32);
                personnageAAfficher.setPositionY(terrain.getYById(id) * CombatGameState.TAILLE_CASE - 16);
            }




            miseAJour();
        }
    }

    public ArrayList<PersonnageNonJoueur> getListePNJAAfficher() {
        return listePNJAAfficher;
    }
}
