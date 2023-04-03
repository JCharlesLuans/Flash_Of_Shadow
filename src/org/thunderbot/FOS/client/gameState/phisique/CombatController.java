package org.thunderbot.FOS.client.gameState.phisique;

import org.newdawn.slick.ControllerListener;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.thunderbot.FOS.client.combatState.CombatGameState;

public class CombatController implements KeyListener, ControllerListener, MouseListener {

    private CombatGameState combatGameState;

    public CombatController(CombatGameState combatGameState) {
        this.combatGameState = combatGameState;
    }
    @Override
    public void keyPressed(int key, char c) {

    }

    @Override
    public void keyReleased(int key, char c) {

    }



    public void controllerLeftPressed(int controller) {

    }
    public void controllerLeftReleased(int controller) {

    }

    public void controllerRightPressed(int controller) {

    }
    public void controllerRightReleased(int controller) {

    }

    public void controllerUpPressed(int controller) {

    }
    public void controllerUpReleased(int controller) {

    }

    public void controllerDownPressed(int controller) {

    }
    public void controllerDownReleased(int controller) {

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
        int id = -1;

        combatGameState.getInterfaceJoueur().mouseClicked(button, x, y, nbClick);
        id = combatGameState.getTerrain().mouseClicked(button, x, y, nbClick);

        // Verification que le joueur a bien clicker sur le terrain
        if (id > 0) {
            // Déplacement du joueur sur la case ou le joueur a cliquer
            combatGameState.getPersonnageAAfficher().setPositionX(combatGameState.getTerrain().getXById(id) * CombatGameState.TAILLE_CASE - 32 + CombatGameState.TAILLE_CASE);
            combatGameState.getPersonnageAAfficher().setPositionY(combatGameState.getTerrain().getYById(id) * CombatGameState.TAILLE_CASE - 16 + CombatGameState.TAILLE_CASE);

            combatGameState.miseAJour();

            System.out.println(combatGameState.getPersonnageAAfficher().getStats()); // log
            combatGameState.getPersonnageAAfficher().getStats().setVieRestante(combatGameState.getPersonnageAAfficher().getStats().getVieRestante() - 1);
        }

        if (combatGameState.getPersonnageAAfficher().getStats().getVieRestante() == 0 || combatGameState.getListePNJAAfficher().size() == 0) {
            combatGameState.fin();
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
    }

    @Override
    public void mouseDragged(int i, int i1, int i2, int i3) {

    }
}
