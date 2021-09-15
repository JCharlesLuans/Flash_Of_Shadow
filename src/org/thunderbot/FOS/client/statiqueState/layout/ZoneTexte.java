/*
 * ZoneTexte.java                 15/09/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.statiqueState.layout;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

/**
 * Layout pour saisir du texte
 *
 * @author Jean-Charles Luans
 */
public class ZoneTexte implements KeyListener {

    private String texte; // Texte afficher et saisie

    private GameContainer gameContainer;

    private int longueur;
    private int hauteur;
    private float x;
    private float y;

    public ZoneTexte(float x, float y, int longueur, int hauteur, GameContainer gameContainer) {
        gameContainer.getInput().addKeyListener(this);
        texte = "";
    }

    @Override
    public void keyPressed(int i, char c) {
        System.out.println(i);
        System.out.println(texte);
    }

    @Override
    public void keyReleased(int i, char c) {

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
