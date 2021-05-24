/*
 * Personnage.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.gameState.entite;

/**
 * Classe personnage, partager entre les clients et le serveurs
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class ServPersonnage {

    protected float positionX; /** Position en X */
    protected float positionY; /** Position en Y */
    protected int direction; /** Position en Y */

    public ServPersonnage() {
        positionX = 300;
        positionY = 300;
        direction = 0;
    }

}
