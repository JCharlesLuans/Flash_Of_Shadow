/*
 * Terrain.java             02/04/2023
 * Copyright et copyleft TNLag Corp.
 */
package org.thunderbot.FOS.client.gameState.world;

import org.newdawn.slick.Graphics;
import org.thunderbot.FOS.client.ChaosRevolt;
import org.thunderbot.FOS.client.combatState.Case;

import static org.thunderbot.FOS.client.combatState.CombatGameState.TAILLE_CASE;
import static org.thunderbot.FOS.client.combatState.CombatGameState.TAILLE_INTERFACE;

/** Representation d'un terrain de combats game state */
public class Terrain {

    int nombreCaseLongueur;
    int nombreCaseHauteur;

    private Case[][] terrain;

    /**
     * Genere tout les cases de se terrain
     */
    public Terrain() {
        int id = 0;
        nombreCaseLongueur = ChaosRevolt.WIDTH / TAILLE_CASE;
        nombreCaseHauteur  = ChaosRevolt.HEIGHT / TAILLE_CASE - TAILLE_INTERFACE;
        terrain = new Case[nombreCaseHauteur][nombreCaseLongueur];

        for (int i = 0; i < nombreCaseHauteur; i++) {
            for (int j = 0; j < nombreCaseLongueur; j++) {
                id ++;
                terrain[i][j] = new Case(id, TAILLE_CASE*j, TAILLE_CASE*i, TAILLE_CASE);
            }
        }
    }

    /**
     * Affichage les cases du terrain
     */
    public void render(Graphics graphics) {
        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[i].length; j++) {
                terrain[i][j].render(graphics);
            }
        }
    }

    public int getNombreCaseHauteur() {
        return nombreCaseHauteur;
    }

    public int mouseClicked(int button, int x, int y, int nbClick) {
        int aRetourner = -1;


        for (int i = 0; i < nombreCaseHauteur; i++) {
            for (int j = 0; j < nombreCaseLongueur; j++) {
                if (terrain[i][j].mouseClicked(button, x, y, nbClick))
                    aRetourner = terrain[i][j].getId();
            }
        }

        return aRetourner;
    }

    /**
     * Retourne l'abscisse d'une case dont l'ID est passer en paramettre. L'ordre de grandeur correspond au terrain
     * @param id de la case
     * @return le x
     */
    public float getXById(int id) {
        int compteur = 0;
        int x = -1;
        nombreCaseLongueur = ChaosRevolt.WIDTH / TAILLE_CASE;
        nombreCaseHauteur  = ChaosRevolt.HEIGHT / TAILLE_CASE - TAILLE_INTERFACE;

        for (int i = 0; i < nombreCaseHauteur; i++) {
            for (int j = 0; j < nombreCaseLongueur; j++) {
                compteur++;
                if (compteur == id) {
                    x = j;
                }
            }
        }

        return x + 1;
    }

    /**
     * Retourne l'ordonnée d'une case dont l'ID est passer en paramettre. L'ordre de grandeur correspond au terrain
     * @param id de la case
     * @return le y
     */
    public float getYById(int id) {
        int compteur = 0;
        int y = -1;
        nombreCaseLongueur = ChaosRevolt.WIDTH / TAILLE_CASE;
        nombreCaseHauteur  = ChaosRevolt.HEIGHT / TAILLE_CASE - TAILLE_INTERFACE;

        for (int i = 0; i < nombreCaseHauteur; i++) {
            for (int j = 0; j < nombreCaseLongueur; j++) {
                compteur++;
                if (compteur == id) {
                    y = i;
                }
            }
        }

        return y + 1;
    }

    /**
     * Retourne un boolean si la case est à la portée des coordonnées mise en paramettre.
     * @param id de la case
     * @param portee a atteindre
     * @param x
     * @param y
     * @return true si a portee false sinon
     */
    public boolean caseAPortee(int id, int portee, float x, float y) {
        // Transformation de l'id en coordonner
        float futurX = getXById(id);
        float futurY = getYById(id);

        float resteX;
        float resteY;

        // Verification si coordonnée > stats.mouvement
        // On va vers la GAUCHE
        if (x > futurX) {
            resteX = x - futurX;
        } else {
            resteX = futurX - x;
        }

        if (y > futurY) {
            resteY = y - futurY;
        } else {
            resteY = futurY - y;
        }

        return resteX + resteY <= portee;
    }
}
