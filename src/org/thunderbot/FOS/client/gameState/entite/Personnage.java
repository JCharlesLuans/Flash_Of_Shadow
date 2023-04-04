package org.thunderbot.FOS.client.gameState.entite;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.thunderbot.FOS.client.gameState.phisique.Stats;
import org.thunderbot.FOS.client.gameState.world.Carte;

import java.util.Arrays;

/**
 * Représente un personnage, qu'il soit joueur ou non joueur.
 */
public class Personnage {
    public static final int HAUT = 0;
    public static final int GAUCHE = 1;
    public static final int BAS = 2;
    public static final int DROITE = 3;

    /** ID du personnage, permettant de le diférancier avce les autre personnage du mêmetype que lui*/
    protected int id;

    /**
     * Indique si le personnage se deplace ou non
     */
    protected boolean moving = false;

    /**
     * Position en X
     */
    protected float positionX;

    /**
     * Position en Y
     */
    protected float positionY;

    /**
     * Direction vers laquelle va le personnage
     */
    protected int direction;

    /**
     * Carte sur laquelle se situe le personnage
     */
    protected String nom;

    /**
     * Animation générer a partir du sprite du personnage
     */
    protected Animation[] animations = new Animation[8];

    /**  Inidique si le personnage est sur un escalier ou pas */
    protected boolean escalierDroite,
                    escalierGauche;

    /** Indique si le personnage subit une colision */
    protected boolean collision;

    /** Indique si le personnage est en combat */
    protected boolean enCombat;

    /** Fichier de sprite */
    protected String sprite;

    /** Stats */
    protected Stats stats;

    /**
     * Charge une animations a partir d'une sprite sheet, en indiquant les début de l'annimation et la fin
     *
     * @param spriteSheet
     * @param startX
     * @param endX
     * @param y
     * @return l'annimation
     */
    public static Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 100);
        }
        return animation;
    }

    public void loadAnimation(SpriteSheet spriteSheet) {
        this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
        this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
        this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
        this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
        this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
        this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
        this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
        this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);
    }

    public void update(Carte carte, int delta) {
        float futurX,
                futurY;

        updateTrigger(carte);

        if (this.moving) {

            // Calcul des de la futur position
            futurX = getFuturX(delta);
            futurY = getFuturY(delta);

            collision = carte.isCollision(futurX, futurY);
            // Gestion des collisions avec un mur
            if (!collision) {
                positionX = futurX;
                positionY = futurY;
            }
        }
    }

    /**
     * Gere les updates de triggerr
     */
    private void updateTrigger(Carte carte) {

        escalierDroite = escalierGauche = false;

        for (int objectID = 0; objectID < carte.getObjectCount(); objectID++) {

            if (carte.isInTrigger(positionX, positionY, objectID)) {
                escalierGauche = "escalierGauche".equals(carte.getObjectType(objectID));
                escalierDroite = "escalierDroite".equals(carte.getObjectType(objectID));
            }
        }
    }

    /**
     * Affichage du personnage sur le graphics passer en parametre
     *
     * @param graphics graphic sur lequel afficher le personnage
     */
    public void render(Graphics graphics) {
        // Application d'un delta pour les collisions
        float positionAnimationX = positionX - 32;
        float positionAnimationY = positionY - 60;

        graphics.drawAnimation(animations[direction + (moving ? 4 : 0)], positionAnimationX, positionAnimationY);
        graphics.drawString(nom, positionX - graphics.getFont().getWidth(nom) / 2, positionY - 65);
    }

    /**
     * Mise a jour réseaux
     * @param personnageJoueur
     */
    public void miseAJour(Personnage personnageJoueur) {
        this.positionX = personnageJoueur.positionX;
        this.positionY = personnageJoueur.positionY;
        this.direction = personnageJoueur.direction;
        this.moving = personnageJoueur.moving;
        //this.nomCarte = personnageJoueur.nomCarte;
    }

    /**
     * @param delta
     * @return la position en X aprés déplacement
     */
    private float getFuturX(int delta) {

        float futurX = positionX;

        switch (this.direction) {
            case DROITE :
                futurX += .1f * delta;
                break;
            case GAUCHE :
                futurX -= .1f * delta;
                break;
        }

        return futurX;
    }

    /**
     * @param delta
     * @return la position en Y aprés déplacement
     */
    private float getFuturY(int delta) {

        float futurY = positionY;

        switch (this.direction) {
            case BAS :
                futurY += .1f * delta;
                break;
            case HAUT :
                futurY -= .1f * delta;
                break;

            case GAUCHE:
                if (escalierDroite) {
                    futurY = positionY + .1f * delta;
                }
                if (escalierGauche) {
                    futurY = positionY - .1f * delta;
                }
                break;

            case 3:
                if (escalierGauche) {
                    futurY = positionY + .1f * delta;
                }
                if (escalierDroite) {
                    futurY = positionY - .1f * delta;
                }
                break;
        }

        return futurY;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setEnCombat(boolean enCombat) { this.enCombat = enCombat; }

    public boolean getEnCombat() { return  enCombat; }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    @Override
    public String toString() {
        return "Personnage{" +
                "id=" + id +
                ", moving=" + moving +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", direction=" + direction +
                ", nom='" + nom + '\'' +
                ", animations=" + Arrays.toString(animations) +
                ", escalierDroite=" + escalierDroite +
                ", escalierGauche=" + escalierGauche +
                ", collision=" + collision +
                ", enCombat=" + enCombat +
                ", sprite='" + sprite + '\'' +
                ", stats=" + stats +
                '}';
    }

    public Stats getStats() {
        return stats;
    }

    public String getSprite() {
        return sprite;
    }
}
