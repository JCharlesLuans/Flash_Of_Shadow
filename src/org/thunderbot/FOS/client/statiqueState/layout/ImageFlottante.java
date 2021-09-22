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
        this.texte = recoupeString(texte);
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

    /**
     * Redécoupe une string pour l'adapter a la taille de la fenetre
     * @param texte la string a découper
     * @return la sring découper
     */
    public String recoupeString(String texte) {
        String[] decoupeEspace;
        String[] decoupeSautLigne;
        String tmp;
        String aRetourner = "";

        decoupeSautLigne = texte.split("\\n");

        // Pour toute les ligne potentielle
        for (int i = 0; i < decoupeSautLigne.length; i++) {
            // on verifie qu'elles rentre dans la feuille
            if (font.getFont().getWidth(decoupeSautLigne[i]) < image.getWidth()) {
                // Si c'est le cas on peux concatener avec le reste
                aRetourner += decoupeSautLigne[i] + "\n";
            } else {
                // On reinitialise la string de travail
                tmp = "";
                // On redecoupe la string qui ne va pas
                decoupeEspace = decoupeSautLigne[i].split(" ");
                for (int j = 0; j < decoupeEspace.length; j++) {
                    // On verifie que l'on peux concatener
                    if (font.getFont().getWidth(decoupeEspace[j] + " " + tmp) < image.getWidth()) {
                        //Si on peux on concatene
                        tmp += decoupeEspace[j] + " ";
                    } else {
                        // Sinon on returne a la ligne, et on concatene le reste du tableau
                        tmp += '\n';
                        for (int k = j; k < decoupeEspace.length; k++) {
                            tmp += decoupeEspace[k] + " ";
                        }
                        // On rapelle la fonction pour redecouper
                        tmp = recoupeString(tmp);
                        // On sort de la boucle
                        break;
                    }
                }
                aRetourner += tmp;
            }
        }

        return aRetourner;
    }

    public int getHeight() {
        return image.getHeight();
    }
}
