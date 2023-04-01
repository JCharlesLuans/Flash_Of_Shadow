package org.thunderbot.FOS.client.gameState.phisique;

import org.newdawn.slick.*;
import org.thunderbot.FOS.client.combatState.CombatGameState;
import org.thunderbot.FOS.client.gameState.MapGameState;
import org.thunderbot.FOS.client.gameState.entite.Camera;
import org.thunderbot.FOS.client.gameState.entite.PersonnageJoueurClient;
import org.thunderbot.FOS.client.gameState.world.Carte;
import org.thunderbot.FOS.client.statiqueState.layout.FenetrePopUpChoix;

/**
 * Objet de controle d'un personnage
 */
public class PersonnageController implements KeyListener, ControllerListener, MouseListener {

    /* Indique les position */
    private static final int HAUT = 0,
            GAUCHE = 1,
            BAS = 2,
            DROITE = 3;

    MapGameState mapGameState;
    GameContainer gameContainer;
    PersonnageJoueurClient personnageJoueurClient; // Personnage a faire bouger
    Carte carte; // Carte avec laquelle le joueur interagie avec son curseur
    Camera camera;

    public PersonnageController(MapGameState mapGameState) {
        this.mapGameState = mapGameState;
        this.personnageJoueurClient = mapGameState.getJoueur();
        this.carte = mapGameState.getCarte();
        this.camera = mapGameState.getCamera();
        this.gameContainer = mapGameState.getGameContainer();
    }


    @Override
    public void keyPressed(int key, char c) {
        switch (key) {
            case Input.KEY_Z:
                personnageJoueurClient.setDirection(HAUT);
                personnageJoueurClient.setMoving(true);
                break;
            case Input.KEY_Q:
                personnageJoueurClient.setDirection(GAUCHE);
                personnageJoueurClient.setMoving(true);
                break;
            case Input.KEY_S:
                personnageJoueurClient.setDirection(BAS);
                personnageJoueurClient.setMoving(true);
                break;
            case Input.KEY_D:
                personnageJoueurClient.setDirection(DROITE);
                personnageJoueurClient.setMoving(true);
                break;
            case Input.KEY_ESCAPE:
                if (personnageJoueurClient.getGui().getMenu().isActive()) {
                    personnageJoueurClient.getGui().getMenu().fermer();
                } else {
                    personnageJoueurClient.getGui().getMenu().ouvrir();
                }
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        personnageJoueurClient.setMoving(false);
    }



    public void controllerLeftPressed(int controller) {
        personnageJoueurClient.setDirection(GAUCHE);
        personnageJoueurClient.setMoving(true);
    }
    public void controllerLeftReleased(int controller) {
        personnageJoueurClient.setMoving(false);
    }

    public void controllerRightPressed(int controller) {
        personnageJoueurClient.setDirection(DROITE);
        personnageJoueurClient.setMoving(true);
    }
    public void controllerRightReleased(int controller) {
        personnageJoueurClient.setMoving(false);
    }

    public void controllerUpPressed(int controller) {
        personnageJoueurClient.setDirection(HAUT);
        personnageJoueurClient.setMoving(true);
    }
    public void controllerUpReleased(int controller) {
        personnageJoueurClient.setMoving(false);
    }

    public void controllerDownPressed(int controller) {
        personnageJoueurClient.setDirection(BAS);
        personnageJoueurClient.setMoving(true);
    }
    public void controllerDownReleased(int controller) {
        personnageJoueurClient.setMoving(false);
    }

    @Override
    public void controllerButtonPressed(int i, int bouton) {


    }

    @Override
    public void controllerButtonReleased(int i, int bouton) {

    }

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }

    @Override
    public void mouseWheelMoved(int i) {

    }

    @Override
    public void mouseClicked(int button, int x, int y, int nbClick) {

        // Click sur le joueur pour declancher son GUI
        personnageJoueurClient.getGui().mouseClicked(x, y);

        // Rectification du X par rapport a la camera
        x += camera.getPositionX() - gameContainer.getWidth() / 2;
        y += camera.getPositionY() - gameContainer.getHeight() / 2;

        personnageJoueurClient.mouseClicked(x, y);
        carte.mouseClicked(personnageJoueurClient, mapGameState ,x, y);
        mapGameState.getFenetreCombat().mouseClicked(button, x, y, nbClick);

        // Gestion de la fenetre des combat
        if (mapGameState.getFenetreCombat().isOui()) {
            mapGameState.getFenetreCombat().init();
            mapGameState.getStateBasedGame().enterState(CombatGameState.ID);
            personnageJoueurClient.enCombat(mapGameState.getFenetreCombat().getInfoComplementaire());
        }
    }

    @Override
    public void mousePressed(int i, int i1, int i2) {

    }

    @Override
    public void mouseReleased(int i, int x, int y) {

    }

    @Override
    public void mouseMoved(int i, int x, int y, int i3) {
        personnageJoueurClient.getGui().mouseMouved(i, x, y, i3);
    }

    @Override
    public void mouseDragged(int i, int i1, int i2, int i3) {

    }
}