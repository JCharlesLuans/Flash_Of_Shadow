package org.thunderbot.FOS.client.gameState.phisique;

import org.newdawn.slick.ControllerListener;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.thunderbot.FOS.client.gameState.entite.PersonnageJoueurClient;

/**
 * Objet de controle d'un personnage
 */
public class PersonnageController implements KeyListener, ControllerListener, MouseListener {

    /* Indique les position */
    private static final int HAUT = 0,
            GAUCHE = 1,
            BAS = 2,
            DROITE = 3;

    PersonnageJoueurClient personnageJoueurClient; // Personnage a faire bouger

    public PersonnageController(PersonnageJoueurClient personnageJoueurClient) {
        this.personnageJoueurClient = personnageJoueurClient;
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
        personnageJoueurClient.getGui().mouseClicked(x, y);
    }

    @Override
    public void mousePressed(int i, int i1, int i2) {

    }

    @Override
    public void mouseReleased(int i, int x, int y) {

    }

    @Override
    public void mouseMoved(int i, int x, int y, int i3) {
        if (personnageJoueurClient.getGui().getMenu().isActive())
            personnageJoueurClient.getGui().getMenu().mouseMouved(x, y);

        personnageJoueurClient.getGui().getFichePersonnage().mouseMouved(x, y);
    }

    @Override
    public void mouseDragged(int i, int i1, int i2, int i3) {

    }
}