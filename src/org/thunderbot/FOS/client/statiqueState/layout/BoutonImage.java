package org.thunderbot.FOS.client.statiqueState.layout;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class BoutonImage {

    private Image imageUnselect;
    private Image imageSelect;
    private boolean selectionner;
    private float x;
    private float y;

    public BoutonImage(String src, float x, float y) throws SlickException {

        String srcImageSelect = src.split("\\.")[0] + "Selected." + src.split("\\.")[1];
        imageUnselect = new Image(src);
        imageSelect = new Image(srcImageSelect);
        this.x = x;
        this.y = y;
    }

    public void render(Graphics graphics) {
        if (selectionner) {
            graphics.drawImage(imageSelect, x, y);
        } else {
            graphics.drawImage(imageUnselect, x, y);
        }
    }

    /**
     * @param x
     * @param y
     * @return true si lees coordonnée sont comprise dans le bouton, false sinon
     */
    public boolean isInLayout(float x, float y) {

        return this.x < x && x < this.x + this.imageSelect.getWidth()
                && this.y < y && y < this.y + imageSelect.getHeight();
    }

    public void setSelectionner(boolean selectionner) {
        this.selectionner = selectionner;
    }

    public boolean isSelectionner() {
        return selectionner;
    }

    public int getWidth() {
        return imageSelect.getWidth();
    }

    public int getHeight() {
        return imageSelect.getHeight();
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
