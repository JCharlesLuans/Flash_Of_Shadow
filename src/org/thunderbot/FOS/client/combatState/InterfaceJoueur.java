/*
 * Interface.java             02/04/2023
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.combatState;

import org.newdawn.slick.*;
import org.thunderbot.FOS.client.gameState.entite.PersonnageJoueurClient;
import org.thunderbot.FOS.database.beans.Competence;

import java.util.ArrayList;

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
    private static final int LONGUEUR = 266; // Longueur maximale de la barre
    private static final int HAUTEUR = 24;

    private float pourcentageVie = 0;
    private float pourcentageMana = 0;
    private float pourcentageMouvement = 0;

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

    private int x, y;

    /** Position du cadre des competence */
    private static final int DELTA_COMPTENCE = 16;
    private static final int DELTA_IMAGE = 4;

    /** Liste des compétence du joueur */
    private Image[] imgCompetence;
    private Case[] interactionCompetence;

    public InterfaceJoueur(GameContainer gameContainer, ArrayList<Competence> listeCompetence, int nombreCaseHauteur) throws SlickException {
        imgFondInterface = new Image("res/combatState/ui.png");
        imgJauge = new Image("res/combatState/jauge.png");
        imgCadreCompetenceSupGauche = new Image("res/combatState/cadreCompetenceSupGauche.png");
        imgCadreCompetenceSupDroite = new Image("res/combatState/cadreCompetenceSupDroite.png");
        imgCadreCompetenceInfGauche = new Image("res/combatState/cadreCompetenceInfGauche.png");
        imgCadreCompetenceInfDroite = new Image("res/combatState/cadreCompetenceInfDroite.png");
        yFondInterface = nombreCaseHauteur * TAILLE_CASE;
        imgCompetence = new Image[listeCompetence.size()];

        // Charge les images des competences
        for (int i = 0; i < listeCompetence.size(); i++) {
            imgCompetence[i] = new Image("res/texture/competence/" + listeCompetence.get(i).getImage());
        }

        x = gameContainer.getWidth() / 2 - imgCadreCompetenceSupGauche.getWidth();
        y = yFondInterface + imgFondInterface.getHeight() / 2 - imgCadreCompetenceSupGauche.getHeight() + DELTA_COMPTENCE;

        // Initialisation des case clicable des competences
        interactionCompetence = new Case[listeCompetence.size()];

        interactionCompetence[0] = new Case(listeCompetence.get(0).getId(), x, y, TAILLE_CASE);

        if (interactionCompetence.length > 1)
            interactionCompetence[1] = new Case(listeCompetence.get(1).getId(),x + TAILLE_CASE, y, TAILLE_CASE);

        if (interactionCompetence.length > 2)
            interactionCompetence[2] = new Case(listeCompetence.get(2).getId(), x, y + TAILLE_CASE, TAILLE_CASE);

        if (interactionCompetence.length > 3)
            interactionCompetence[3] = new Case(listeCompetence.get(3).getId(), x + TAILLE_CASE, y + TAILLE_CASE, TAILLE_CASE);

    }

    public void render(GameContainer gameContainer, Graphics graphics) {
        imgFondInterface.draw(0, yFondInterface, gameContainer.getWidth(), gameContainer.getHeight());

        graphics.setColor(COULEUR_VIE);
        graphics.fillRect(DEBUT_X, DEBUT_Y_VIE, pourcentageVie, HAUTEUR);

        graphics.setColor(COULEUR_MANA);
        graphics.fillRect(DEBUT_X, DEBUT_Y_MANA, pourcentageMana, HAUTEUR);

        graphics.setColor(COULEUR_MOUV);
        graphics.fillRect(DEBUT_X, DEBUT_Y_MOUV, pourcentageMouvement, HAUTEUR);

        imgJauge.draw(DEBUT_X - 15, DEBUT_Y_VIE - 4, LONGUEUR + 30, HAUTEUR + 7);
        imgJauge.draw(DEBUT_X - 15, DEBUT_Y_MANA - 4, LONGUEUR + 30, HAUTEUR + 7);
        imgJauge.draw(DEBUT_X - 15, DEBUT_Y_MOUV - 4, LONGUEUR + 30, HAUTEUR + 7);

        imgCadreCompetenceSupGauche.draw(x, y);
        imgCompetence[0].draw(x, y);

        imgCadreCompetenceSupDroite.draw(x + TAILLE_CASE, y);
        if (imgCompetence.length > 1)
            imgCompetence[1].draw(x + TAILLE_CASE, y);

        imgCadreCompetenceInfGauche.draw(x, y + TAILLE_CASE);
        if (imgCompetence.length > 2)
            imgCompetence[2].draw(x, y + TAILLE_CASE);

        imgCadreCompetenceInfDroite.draw(x + TAILLE_CASE, y + TAILLE_CASE);
        if (imgCompetence.length > 3)
            imgCompetence[3].draw(x + TAILLE_CASE, y + TAILLE_CASE);

    }

    public void mouseClicked(int button, int x, int y, int nbClick) {
        for (Case caseSurClick : interactionCompetence) {
            if (caseSurClick.mouseClicked(button, x, y, nbClick)) {
                System.out.println(caseSurClick.getId());
                // TODO Action
            }
        }
    }

    public void update(PersonnageJoueurClient personnageAAfficher) {
        if (personnageAAfficher.getStats().getVieRestante() >= 0)
            pourcentageVie = (personnageAAfficher.getStats().getVieRestante() * LONGUEUR) / personnageAAfficher.getStats().getVieMax();

        if (personnageAAfficher.getStats().getManaRestante() >= 0)
            pourcentageMana = (personnageAAfficher.getStats().getManaRestante() * LONGUEUR) / personnageAAfficher.getStats().getManaMax();

        if (personnageAAfficher.getStats().getMouvementsRestants() >= 0)
            pourcentageMouvement = (personnageAAfficher.getStats().getMouvementsMax() * LONGUEUR) / personnageAAfficher.getStats().getMouvementsMax();
    }
}
