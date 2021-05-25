/*
 * Personnage.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.gameState.entite;

import org.newdawn.slick.Graphics;

import java.io.Serializable;

/**
 * Classe personnage, partager entre les clients et le serveurs
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class ServPersonnage implements Serializable {

    protected float positionX; /** Position en X */
    protected float positionY; /** Position en Y */
    protected int direction; /** Position en Y */

    private String pseudo; /** Pseudo du joueur / nom du personnage */

    public ServPersonnage() {
        positionX = 300;
        positionY = 300;
        direction = 0;
    }

    public void render(Graphics graphics) {
        //stub
        graphics.drawRect(positionX, positionY, 64, 64);
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public int getDirection() {
        return direction;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
