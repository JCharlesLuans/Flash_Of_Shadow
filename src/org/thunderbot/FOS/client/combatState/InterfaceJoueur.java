/*
 * Interface.java             02/04/2023
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.combatState;

import org.newdawn.slick.*;

import static org.thunderbot.FOS.client.combatState.CombatGameState.TAILLE_CASE;

/**
 * Interface affichant les information essancielle au joueur pour les combats.
 * Gere l'affichage de la vie, de la mana et des point mouvements
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class InterfaceJoueur {

    /** Placement des jauges */
    public static final int DEBUT_X = 64;
    public static final int DEBUT_Y_VIE = 591;
    public static final int DEBUT_Y_MANA = 636;
    public static final int DEBUT_Y_MOUV= 682;

    /** Couleur des jauges */
    private static final Color COULEUR_VIE  = new Color(255, 0, 0);
    private static final Color COULEUR_MANA = new Color(0, 0, 255);
    private static final Color COULEUR_MOUV = new Color(0, 255, 0);

    /** Tailles des jauges */
    private final int LONGUEUR = 266; // Longueur maximale de la barre
    private final int HAUTEUR = 24;

    /** Coordonnée de placement du fond de l'interface */
    private int yFondInterface;

    /** Image de base, contenant toute les jauges ainsi que les cadres */
    private Image imgFondInterface;
    private Image imgJauge;

    /** Image des compétances */
    private Image imgCadreCompetenceSupGauche;
    private Image imgCadreCompetenceSupDroite;
    private Image imgCadreCompetenceInfGauche;
    private Image imgCadreCompetenceInfDroite;

    /** Position du cadrre ds competence */
    private static final int DELTA_COMPTENCE = 16;


    public InterfaceJoueur(int nombreCaseHauteur) throws SlickException {
        imgFondInterface = new Image("res/combatState/ui.png");
        imgJauge = new Image("res/combatState/jauge.png");
        imgCadreCompetenceSupGauche = new Image("res/combatState/cadreCompetenceSupGauche.png");
        imgCadreCompetenceSupDroite = new Image("res/combatState/cadreCompetenceSupDroite.png");
        imgCadreCompetenceInfGauche = new Image("res/combatState/cadreCompetenceInfGauche.png");
        imgCadreCompetenceInfDroite = new Image("res/combatState/cadreCompetenceInfDroite.png");
        yFondInterface = nombreCaseHauteur * TAILLE_CASE;
    }

    public void render(GameContainer gameContainer, Graphics graphics) {
        imgFondInterface.draw(0, yFondInterface, gameContainer.getWidth(), gameContainer.getHeight());

        graphics.setColor(COULEUR_VIE);
        graphics.fillRect(DEBUT_X, DEBUT_Y_VIE, LONGUEUR, HAUTEUR);

        graphics.setColor(COULEUR_MANA);
        graphics.fillRect(DEBUT_X, DEBUT_Y_MANA, LONGUEUR, HAUTEUR);

        graphics.setColor(COULEUR_MOUV);
        graphics.fillRect(DEBUT_X, DEBUT_Y_MOUV, LONGUEUR, HAUTEUR);

        imgJauge.draw(DEBUT_X - 15, DEBUT_Y_VIE - 4, LONGUEUR + 30, HAUTEUR + 7);
        imgJauge.draw(DEBUT_X - 15, DEBUT_Y_MANA - 4, LONGUEUR + 30, HAUTEUR + 7);
        imgJauge.draw(DEBUT_X - 15, DEBUT_Y_MOUV - 4, LONGUEUR + 30, HAUTEUR + 7);

        imgCadreCompetenceSupGauche.draw(gameContainer.getWidth() / 2 - imgCadreCompetenceSupGauche.getWidth(), yFondInterface + imgFondInterface.getHeight() / 2 - imgCadreCompetenceSupGauche.getHeight() + DELTA_COMPTENCE);
        imgCadreCompetenceSupDroite.draw(gameContainer.getWidth() / 2, yFondInterface + imgFondInterface.getHeight() / 2 - imgCadreCompetenceSupDroite.getHeight() + DELTA_COMPTENCE);

        imgCadreCompetenceInfGauche.draw(gameContainer.getWidth() / 2 - imgCadreCompetenceSupGauche.getWidth(), yFondInterface + imgFondInterface.getHeight() / 2 + DELTA_COMPTENCE);
        imgCadreCompetenceInfDroite.draw(gameContainer.getWidth() / 2, yFondInterface + imgFondInterface.getHeight() / 2 + DELTA_COMPTENCE);
    }

}
