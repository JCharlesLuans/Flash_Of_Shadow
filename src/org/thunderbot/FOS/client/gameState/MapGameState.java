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
import org.thunderbot.FOS.client.gameState.GUI.Gui;
import org.thunderbot.FOS.client.gameState.entite.Camera;
import org.thunderbot.FOS.client.gameState.entite.Personnage;
import org.thunderbot.FOS.client.gameState.entite.PersonnageJoueur;
import org.thunderbot.FOS.client.gameState.phisique.PersonnageController;
import org.thunderbot.FOS.client.gameState.world.Carte;
import org.thunderbot.FOS.client.network.Client;

import java.util.ArrayList;

/**
 * State du monde de base
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class MapGameState extends BasicGameState {

    /** Carte afficher a l'écran */
    Carte carte;

    /** Client pour la communication multijoueur et liste des joueurs connecter */
    Client client;
    ArrayList<PersonnageJoueur> listeJoueur;

    /** Personnage */
    Personnage joueur;

    /** Camera */
    Camera camera;

    PersonnageController personnageController;

    public static final int ID = 2;

    public MapGameState(Client client) {
        this.client = client;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        listeJoueur = new ArrayList<>();

        carte = new Carte();

        joueur = new Personnage(client);
        joueur.setGui(new Gui(container, client, game));

        camera = new Camera(joueur);
        personnageController = new PersonnageController(joueur);

        container.getInput().addKeyListener(personnageController);
        container.getInput().addControllerListener(personnageController);
        container.getInput().addMouseListener(personnageController);
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        carte.setChangeCarte(true);
        carte.changeMap(client.getPersonnage().getMap().getNom());
        joueur.setPositionX(client.getPersonnage().getX());
        joueur.setPositionY(client.getPersonnage().getY());
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

        camera.render(gameContainer, graphics);
        carte.renderBackground();
        joueur.render(graphics);

        // Affichage des autres joueur
        if (listeJoueur.size() > 0) {
            for (int i = 0; i < listeJoueur.size(); i++) {
                listeJoueur.get(i).render(graphics);
            }
        }

        carte.renderForeground();
        joueur.getGui().render(graphics);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

        // UPDATTE DU JOUEUR
        if (!client.socketIsClosed()) {
            joueur.update(carte, delta);
            camera.update(container, carte);
            updateListeJoueur(client.updateServeurMouvement()); //Envoi des data au joueur
        }

    }

    @Override
    public void keyReleased(int key, char c) {

    }

    @Override
    public int getID() {
        return ID;
    }

    public Personnage getJoueur() {
        return joueur;
    }

    public void updateListeJoueur(ArrayList<org.thunderbot.FOS.database.beans.Personnage> listeDistante) throws SlickException {
        listeJoueur = new ArrayList<>();
        for (int i = 0; i < listeDistante.size(); i++) {
            PersonnageJoueur tmp = new PersonnageJoueur(listeDistante.get(i).getNom(), listeDistante.get(i).getDirection(), listeDistante.get(i).getX(), listeDistante.get(i).getY());
            tmp.setMoving(listeDistante.get(i).isMoving());
            listeJoueur.add(tmp);
        }
    }
}
