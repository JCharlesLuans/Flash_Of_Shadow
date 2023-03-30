/*
 * ImageFlottante.java                 16/09/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.statiqueState.layout;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.thunderbot.FOS.client.statiqueState.police.MedievalSharp;
import org.thunderbot.FOS.utils.Tools;

/**
 * TODO ecrire Java Doc
 *
 * @author Jean-Charles Luans
 */
public class ImageFlottante {

    private boolean visible;
    private float x;
    private  float y;
    private Image image;
    private String texte;
    private MedievalSharp font;

    public ImageFlottante(String src) throws SlickException {
        image = new Image(src);
        x = y = 0.0f;
        texte = "";
        visible = false;
        font = new MedievalSharp(38);
    }

    public void render(Graphics graphics) {
        if (visible) {
            graphics.drawImage(image, x, y);
            graphics.drawString(texte, x + 15, y + 10);
        }
    }

    public void setTexte(String texte) {
        this.texte = Tools.recoupeString(texte, image, font);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getHeight() {
        return image.getHeight();
    }
}
