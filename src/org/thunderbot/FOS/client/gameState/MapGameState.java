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
import org.newdawn.slick.util.xml.XMLElementList;
import org.thunderbot.FOS.client.gameState.GUI.Gui;
import org.thunderbot.FOS.client.gameState.entite.*;
import org.thunderbot.FOS.client.gameState.phisique.PersonnageController;
import org.thunderbot.FOS.client.gameState.world.Carte;
import org.thunderbot.FOS.client.network.Client;
import org.thunderbot.FOS.client.statiqueState.layout.FenetrePopUpChoix;
import org.thunderbot.FOS.database.beans.PNJ;

import java.util.ArrayList;

/**
 * State du monde de base
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class MapGameState extends BasicGameState {

    /** ID de la state */
    public static final int ID = 2;

    private static final String INF_COMBAT = "Voulez vous vraiment entrer en combat contre ";

    /** Fenetre */
    private GameContainer gameContainer;

    /** Carte afficher a l'écran */
    private Carte carte;

    /** Client pour la communication multijoueur et liste des joueurs connecter */
    private Client client;
    private ArrayList<PersonnageJoueur> listeJoueur;
    private ArrayList<PersonnageNonJoueur> listePNJ;

    /** Personnage */
    private PersonnageJoueurClient joueur;

    /** Camera */
    private Camera camera;

    /** Gere les inputs du joueur*/
    private PersonnageController personnageController;

    /** Indique si le joueur rentre dans un combat ou pas */
    private boolean combat;

    /** Fentre de confirmation pour le combat */
    private FenetrePopUpChoix fenetreCombat;

    /** */
    private StateBasedGame stateBasedGame;

    /** liste des joueur distant qu'on l'on recois par le serveur */
    private static ArrayList<org.thunderbot.FOS.database.beans.Personnage> listePersonnageAJour;

    public MapGameState(Client client) {
        this.client = client;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.stateBasedGame = stateBasedGame;
        this.gameContainer = gameContainer;
        this.fenetreCombat = new FenetrePopUpChoix(INF_COMBAT);
        listeJoueur = new ArrayList<>();
        listePNJ = new ArrayList<>();
        listePersonnageAJour = new ArrayList<>();
        carte = new Carte();
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        joueur = new PersonnageJoueurClient(client);
        joueur.setGui(new Gui(gameContainer, client, stateBasedGame));
        camera = new Camera(joueur);
        personnageController = new PersonnageController(this);

        carte.setChangeCarte(true);
        carte.changeMap(client.getPersonnage().getMap().getNom(), client);
        joueur.setPositionX(client.getPersonnage().getX());
        joueur.setPositionY(client.getPersonnage().getY());

        gameContainer.getInput().addKeyListener(personnageController);
        gameContainer.getInput().addControllerListener(personnageController);
        gameContainer.getInput().addMouseListener(personnageController);
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        gameContainer.getInput().removeAllControllerListeners();
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

        if (listePNJ.size() > 0) {
            for (int i = 0; i < listePNJ.size(); i++) {
                listePNJ.get(i).render(graphics);
            }
        }

        carte.renderForeground();
        joueur.getGui().render(graphics);
        fenetreCombat.render(graphics);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

        // UPDATTE DU JOUEUR
        if (!client.socketIsClosed()) {
            joueur.update(carte, delta);
            camera.update(container, carte);
            updateListeJoueur(client.updateServeurMouvementJoueurs(), listeJoueur); //Envoi des data au joueur local
            updateListePNJ(client.updateServeurMouvementPNJ(), listePNJ);
        }



        if (combat && !fenetreCombat.isShow()) {
            fenetreCombat.setShow(true);
            combat = false;
        }

    }

    @Override
    public void keyReleased(int key, char c) {

    }

    @Override
    public int getID() {
        return ID;
    }

    public PersonnageJoueurClient getJoueur() {
        return joueur;
    }

    public void updateListeJoueur(ArrayList<org.thunderbot.FOS.database.beans.Personnage> listeDistante, ArrayList<PersonnageJoueur> listeLocale) throws SlickException {

        boolean existe = false;

        // Effacement des joueur qui n'existe plus sur la carte
        for (int i = 0; i < listeLocale.size(); i ++) {
            existe = false;

            // Y'a t'il des joueur dans la liste distante ?
            if (listeDistante.size() > 0) {
                for (int j = 0; j < listeDistante.size(); j++) {
                    existe = listeLocale.get(i).getId() == listeDistante.get(j).getId();
                    if (existe)
                        break;
                }

                if (!existe) {
                    listeLocale.remove(listeLocale.get(i));
                }
            } else {
                listeLocale.remove(listeLocale.get(i));
            }
        }

        for (int i = 0; i < listeDistante.size(); i++) {
            PersonnageJoueur tmp = new PersonnageJoueur(listeDistante.get(i).getId(), listeDistante.get(i).getNom(), listeDistante.get(i).getDirection(), listeDistante.get(i).getX(), listeDistante.get(i).getY(), listeDistante.get(i).getSprite());
            tmp.setMoving(listeDistante.get(i).isMoving());


            if (listeLocale.size() > 0) {
                // Mise a jour des personnage
                for (int j = 0; j < listeLocale.size(); j++) {

                    if (listeLocale.get(j).getId() == tmp.getId()) {
                        listeLocale.get(i).miseAJour(tmp);
                    }
                }
            } else {
                // Ajout d'un personnage qui arrive
                listeLocale.add(tmp);

                // TODO LOG
                System.out.println("Ajout");
            }
            listeDistante.remove(listeDistante.get(i));
        }
    }



    public void updateListePNJ(ArrayList<PNJ> listeDistante, ArrayList<PersonnageNonJoueur> listeLocale) throws SlickException {

        boolean existe = false;

        // Effacement des joueur qui n'existe plus sur la carte
        for (int i = 0; i < listeLocale.size(); i ++) {
            existe = false;

            // Y'a t'il des joueur dans la liste distante ?
            if (listeDistante.size() > 0) {
                for (int j = 0; j < listeDistante.size(); j++) {
                    existe = listeLocale.get(i).getId() == listeDistante.get(j).getId();
                    if (existe)
                        break;
                }

                if (!existe) {
                    listeLocale.remove(listeLocale.get(i));
                }
            } else {
                listeLocale.remove(listeLocale.get(i));
            }
        }

        for (int i = 0; i < listeDistante.size(); i++) {
            PersonnageNonJoueur tmp = new PersonnageNonJoueur(listeDistante.get(i));
            tmp.setMoving(true);

            if (listeLocale.size() > 0) {
                // Mise a jour des personnages
                for (int j = 0; j < listeLocale.size(); j++) {

                    if (listeLocale.get(j).getId() == tmp.getId()) {
                        listeLocale.get(i).miseAJour(tmp);
                    }
                }
            } else {
                // Ajout d'un personnage qui arrive
                listeLocale.add(tmp);
            }
            listeDistante.remove(listeDistante.get(i));
        }
    }

    public Carte getCarte() {
        return carte;
    }

    public Camera getCamera() {
        return camera;
    }

    public GameContainer getGameContainer() {
        return gameContainer;
    }

    public FenetrePopUpChoix getFenetreCombat() {
        return fenetreCombat;
    }

    public void setCombat(boolean combat) {
        this.combat = combat;
    }

    public StateBasedGame getStateBasedGame() {
        return stateBasedGame;
    }

    public ArrayList<PersonnageNonJoueur> getListePnj() {
        return listePNJ;
    }
}
