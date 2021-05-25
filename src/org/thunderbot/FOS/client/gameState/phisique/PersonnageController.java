package org.thunderbot.FOS.client.gameState.phisique;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.thunderbot.FOS.client.gameState.entite.Personnage;

/**
 * Objet de controle d'un personnage
 */
public class PersonnageController implements KeyListener {

    private Personnage personnage;

    /**
     * Attribue un nouveau controller au personnage passer en paramettre
     * @param personnage a controller
     */
    public PersonnageController(Personnage personnage) {
        this.personnage = personnage;
    }

    @Override
    public void keyPressed(int key, char c) {
        switch (key) {
            case Input.KEY_UP:    personnage.setDirection(0); personnage.setMoving(true); break;
            case Input.KEY_LEFT:  personnage.setDirection(1); personnage.setMoving(true); break;
            case Input.KEY_DOWN:  personnage.setDirection(2); personnage.setMoving(true); break;
            case Input.KEY_RIGHT: personnage.setDirection(3); personnage.setMoving(true); break;
        }
    }

    @Override
    public void keyReleased(int i, char c) {
        personnage.setMoving(false);
    }

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return false;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }
}
