package org.thunderbot.FOS.client.statiqueState.layout;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class BoutonCochable extends Bouton{

    private boolean cocher;
    private Object object;

    public BoutonCochable(float x, float y, int longeur, int hauteur, String texte) {
        super(x, y, longeur, hauteur, texte);
    }

    public void render(Graphics graphics) {


        if (cocher) {
            graphics.setColor(Color.blue);
        }

        Font font = graphics.getFont();
        graphics.drawRect(x, y, longueur, hauteur);
        graphics.drawString(texte,
                x + (float) (longueur / 2 - font.getWidth(texte) / 2),
                y + (float) (hauteur / 2 - font.getHeight(texte) / 2));

        graphics.setColor(Color.white);
    }

    public boolean isCocher() {
        return cocher;
    }

    public void setCocher(boolean cocher) {
        this.cocher = cocher;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
