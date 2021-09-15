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
import org.thunderbot.FOS.client.gameState.entite.Camera;
import org.thunderbot.FOS.client.gameState.entite.Personnage;
import org.thunderbot.FOS.client.gameState.entite.ServPersonnage;
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
    ArrayList<ServPersonnage> listeJoueur;

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
        joueur = new Personnage();
        camera = new Camera(joueur);
        personnageController = new PersonnageController(joueur);

        container.getInput().addKeyListener(personnageController);
        container.getInput().addControllerListener(personnageController);
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        //client.updateClient(this); // Création du thread qui recois les données
        carte.changeMap(client.getPersonnage().getMap());
        joueur.setPseudo(client.getPseudo());
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
                //TODO prendre en compte la carte
            }
        }

        carte.renderForeground();
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

        // UPDATTE DU JOUEUR
        joueur.update(carte, delta);
        camera.update(container, carte);
        //client.updateServeur(this); //Envoi des data au joueur

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

    public ArrayList<ServPersonnage> getListeJoueur() {
        return listeJoueur;
    }
}
