/*
 * ImageFlottante.java                 16/09/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.statiqueState.layout;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

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

    public ImageFlottante(String src) throws SlickException {
        image = new Image(src);
        x = y = 0.0f;
        visible = false;
    }

    public void render(Graphics graphics) {
        if (visible) {
            graphics.drawImage(image, x, y);
        }
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
}
