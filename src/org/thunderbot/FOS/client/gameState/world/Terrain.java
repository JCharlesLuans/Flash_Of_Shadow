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
}
