/* Bouton.java             08/09/2021
 * Copyright et copyleft ThunderBot
 */
package org.thunderbot.FOS.client.statiqueState.layout;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class Bouton extends Layout {

    public Bouton(float x, float y, int longeur, int hauteur, String texte) {
        super(x, y, longeur, hauteur, texte);
    }

    public boolean isInBouton(float x, float y) {
        return isInLayout(x, y);
    }

    @Override
    public void render(Graphics graphics) {
        Font font = graphics.getFont();

        graphics.drawRect(x, y, longueur, hauteur);
        graphics.drawString(texte,
                         x + (float) (longueur / 2 - font.getWidth(texte) / 2),
                         y + (float) (hauteur / 2 - font.getHeight(texte) / 2));
    }

}
