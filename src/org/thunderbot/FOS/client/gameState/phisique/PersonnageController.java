package org.thunderbot.FOS.client.gameState.phisique;

import org.newdawn.slick.ControllerListener;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.thunderbot.FOS.client.gameState.entite.Personnage;

/**
 * Objet de controle d'un personnage
 */
public class PersonnageController implements KeyListener, ControllerListener, MouseListener {

    /* Indique les position */
    private static final int HAUT = 0,
            GAUCHE = 1,
            BAS = 2,
            DROITE = 3;

    Personnage personnage; // Personnage a faire bouger

    public PersonnageController(Personnage personnage) {
        this.personnage = personnage;
    }


    @Override
    public void keyPressed(int key, char c) {
        switch (key) {
            case Input.KEY_Z:
                personnage.setDirection(HAUT);
                personnage.setMoving(true);
                break;
            case Input.KEY_Q:
                personnage.setDirection(GAUCHE);
                personnage.setMoving(true);
                break;
            case Input.KEY_S:
                personnage.setDirection(BAS);
                personnage.setMoving(true);
                break;
            case Input.KEY_D:
                personnage.setDirection(DROITE);
                personnage.setMoving(true);
                break;
            case Input.KEY_ESCAPE:
                if (personnage.getGui().getMenu().isActive()) {
                    personnage.getGui().getMenu().fermer();
                } else {
                    personnage.getGui().getMenu().ouvrir();
                }
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        personnage.setMoving(false);
    }



    public void controllerLeftPressed(int controller) {
        personnage.setDirection(GAUCHE);
        personnage.setMoving(true);
    }
    public void controllerLeftReleased(int controller) {
        personnage.setMoving(false);
    }

    public void controllerRightPressed(int controller) {
        personnage.setDirection(DROITE);
        personnage.setMoving(true);
    }
    public void controllerRightReleased(int controller) {
        personnage.setMoving(false);
    }

    public void controllerUpPressed(int controller) {
        personnage.setDirection(HAUT);
        personnage.setMoving(true);
    }
    public void controllerUpReleased(int controller) {
        personnage.setMoving(false);
    }

    public void controllerDownPressed(int controller) {
        personnage.setDirection(BAS);
        personnage.setMoving(true);
    }
    public void controllerDownReleased(int controller) {
        personnage.setMoving(false);
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
        personnage.getGui().mouseClicked(x, y);
    }

    @Override
    public void mousePressed(int i, int i1, int i2) {

    }

    @Override
    public void mouseReleased(int i, int x, int y) {

    }

    @Override
    public void mouseMoved(int i, int x, int y, int i3) {
        if (personnage.getGui().getMenu().isActive())
            personnage.getGui().getMenu().mouseMouved(x, y);

        personnage.getGui().getFichePersonnage().mouseMouved(x, y);
    }

    @Override
    public void mouseDragged(int i, int i1, int i2, int i3) {

    }
}