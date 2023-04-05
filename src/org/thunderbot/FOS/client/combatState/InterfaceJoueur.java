/*
 * Interface.java             02/04/2023
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.combatState;

import org.newdawn.slick.*;
import org.thunderbot.FOS.client.gameState.entite.PersonnageJoueurClient;
import org.thunderbot.FOS.client.statiqueState.layout.Bouton;
import org.thunderbot.FOS.client.statiqueState.layout.BoutonImage;
import org.thunderbot.FOS.client.statiqueState.layout.ImageFlottante;
import org.thunderbot.FOS.client.statiqueState.police.MedievalSharp;
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

    MedievalSharp police;

    CombatGameState combatGameState; // Sur lequelle est afficher l'interface

    /** Placement des jauges */
    public static final int DEBUT_X = 64;

    public int positionYVieJauge = 0;
    public int positionYManaJauge = 0;
    public int positionYMouvementJauge= 0;
    public int positionYActionJauge = 0;

    /** Couleur des jauges */
    private static final Color COULEUR_VIE  = new Color(255, 0, 0);
    private static final Color COULEUR_MANA = new Color(0, 0, 255);
    private static final Color COULEUR_MOUV = new Color(0, 255, 0);
    private static final Color COULEUR_ACTION = new Color(255, 128, 0);

    /** Tailles des jauges */
    private static final int LONGUEUR = 266; // Longueur maximale de la barre
    private static final int HAUTEUR = 24;

    private static final int DELTA_JAUGE = 45;

    private float pourcentageVie = 0;
    private float pourcentageMana = 0;
    private float pourcentageMouvement = 0;
    private float pourcentageAction = 0;

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

    private int positionXCadreCompetenceSupGauche,
                positionYCadreCompetenceSupGauche;

    private ImageFlottante imageDesc;

    /** Position du cadre des competence */
    private static final int DELTA_COMPTENCE = 16;

    /** Liste des compétence du joueur */
    private Image[] imgCompetence;
    private Case[] interactionCompetence;

    /** Indique les cases sur lesquelle il est possible d'utiliser une compétance */
    private ArrayList<Case> possiblementUtiliser;
    private boolean attanteJoueur = false; // Bloque l'interface jusqu'a ce que le joueur ai fait un clqiue sur le terrain
    private int idCompetence;

    /** Bouton suivant */
    public static final String TXT_BOUTON = "Tour suivant";
    public static final int LARGEUR_BOUTON = 32;
    public static final int LONGUEUR_BOUTON = 64;

    Bouton btnSuivant;

    public InterfaceJoueur(GameContainer gameContainer, CombatGameState combatGameState, ArrayList<Competence> listeCompetence) throws SlickException {

        police = new MedievalSharp(15);

        this.combatGameState = combatGameState;

        imgFondInterface = new Image("res/combatState/ui.png");
        imgJauge = new Image("res/combatState/jauge.png");
        imgCadreCompetenceSupGauche = new Image("res/combatState/cadreCompetenceSupGauche.png");
        imgCadreCompetenceSupDroite = new Image("res/combatState/cadreCompetenceSupDroite.png");
        imgCadreCompetenceInfGauche = new Image("res/combatState/cadreCompetenceInfGauche.png");
        imgCadreCompetenceInfDroite = new Image("res/combatState/cadreCompetenceInfDroite.png");
        yFondInterface = combatGameState.getTerrain().getNombreCaseHauteur() * TAILLE_CASE;
        imgCompetence = new Image[listeCompetence.size()];

        // Charge les images des competences
        for (int i = 0; i < listeCompetence.size(); i++) {
            imgCompetence[i] = new Image("res/texture/competence/" + listeCompetence.get(i).getImage());
        }

        positionXCadreCompetenceSupGauche = gameContainer.getWidth() / 2 - imgCadreCompetenceSupGauche.getWidth();
        positionYCadreCompetenceSupGauche = yFondInterface + imgFondInterface.getHeight() / 2 - imgCadreCompetenceSupGauche.getHeight() + DELTA_COMPTENCE;

        positionYVieJauge = yFondInterface + DELTA_COMPTENCE;
        positionYManaJauge = positionYVieJauge + DELTA_JAUGE;
        positionYMouvementJauge = positionYManaJauge + DELTA_JAUGE;
        positionYActionJauge = positionYMouvementJauge + DELTA_JAUGE;

        // Initialisation des case clicable des competences
        interactionCompetence = new Case[4];

        interactionCompetence[0] = new Case(listeCompetence.get(0).getId(), positionXCadreCompetenceSupGauche, positionYCadreCompetenceSupGauche, TAILLE_CASE);

        if (interactionCompetence.length > 1)
            interactionCompetence[1] = new Case(listeCompetence.get(1).getId(), positionXCadreCompetenceSupGauche + TAILLE_CASE, positionYCadreCompetenceSupGauche, TAILLE_CASE);

        if (interactionCompetence.length > 2)
            interactionCompetence[2] = new Case(listeCompetence.get(2).getId(), positionXCadreCompetenceSupGauche, positionYCadreCompetenceSupGauche + TAILLE_CASE, TAILLE_CASE);

        if (interactionCompetence.length > 3)
            interactionCompetence[3] = new Case(listeCompetence.get(3).getId(), positionXCadreCompetenceSupGauche + TAILLE_CASE, positionYCadreCompetenceSupGauche + TAILLE_CASE, TAILLE_CASE);

        // Initialisation de l'image de description flottante
        imageDesc = new ImageFlottante("res/menuState/gui/fondTexte.png");

        // Initialisation du bouton suivant, pour indiquer la fin de son tour
        btnSuivant = new Bouton(((DEBUT_X + LONGUEUR) + positionXCadreCompetenceSupGauche) / 2, yFondInterface + imgFondInterface.getHeight() / 2 + LARGEUR_BOUTON / 2, LONGUEUR_BOUTON, LARGEUR_BOUTON, TXT_BOUTON);

    }

    public void render(GameContainer gameContainer, Graphics graphics) {

        graphics.setFont(police.getFont());

        imgFondInterface.draw(0, yFondInterface, gameContainer.getWidth(), gameContainer.getHeight());

        graphics.setColor(COULEUR_VIE);
        graphics.fillRect(DEBUT_X, positionYVieJauge, pourcentageVie, HAUTEUR);

        graphics.setColor(COULEUR_MANA);
        graphics.fillRect(DEBUT_X, positionYManaJauge, pourcentageMana, HAUTEUR);

        graphics.setColor(COULEUR_MOUV);
        graphics.fillRect(DEBUT_X, positionYMouvementJauge, pourcentageMouvement, HAUTEUR);

        graphics.setColor(COULEUR_ACTION);
        graphics.fillRect(DEBUT_X, positionYActionJauge, pourcentageAction, HAUTEUR);

        imgJauge.draw(DEBUT_X - 15, positionYVieJauge - 4, LONGUEUR + 30, HAUTEUR + 7);
        imgJauge.draw(DEBUT_X - 15, positionYManaJauge - 4, LONGUEUR + 30, HAUTEUR + 7);
        imgJauge.draw(DEBUT_X - 15, positionYMouvementJauge - 4, LONGUEUR + 30, HAUTEUR + 7);
        imgJauge.draw(DEBUT_X - 15, positionYActionJauge - 4, LONGUEUR + 30, HAUTEUR + 7);

        imgCadreCompetenceSupGauche.draw(positionXCadreCompetenceSupGauche, positionYCadreCompetenceSupGauche);
        imgCompetence[0].draw(positionXCadreCompetenceSupGauche, positionYCadreCompetenceSupGauche);

        imgCadreCompetenceSupDroite.draw(positionXCadreCompetenceSupGauche + TAILLE_CASE, positionYCadreCompetenceSupGauche);
        if (imgCompetence.length > 1)
            imgCompetence[1].draw(positionXCadreCompetenceSupGauche + TAILLE_CASE, positionYCadreCompetenceSupGauche);

        imgCadreCompetenceInfGauche.draw(positionXCadreCompetenceSupGauche, positionYCadreCompetenceSupGauche + TAILLE_CASE);
        if (imgCompetence.length > 2)
            imgCompetence[2].draw(positionXCadreCompetenceSupGauche, positionYCadreCompetenceSupGauche + TAILLE_CASE);

        imgCadreCompetenceInfDroite.draw(positionXCadreCompetenceSupGauche + TAILLE_CASE, positionYCadreCompetenceSupGauche + TAILLE_CASE);
        if (imgCompetence.length > 3)
            imgCompetence[3].draw(positionXCadreCompetenceSupGauche + TAILLE_CASE, positionYCadreCompetenceSupGauche + TAILLE_CASE);


        imageDesc.render(graphics);


        btnSuivant.render(graphics);

    }

    public void mouseMoved(int i, int x, int y, int i3) {
        Competence competenceAAfficher = new Competence();
        boolean visible = false;
        int id;

        for (int j = 0; j < interactionCompetence.length; j++) {
            visible = interactionCompetence[j].inCase(x + TAILLE_CASE, y - TAILLE_CASE) || visible;
            if (interactionCompetence[j].inCase(x + TAILLE_CASE, y - TAILLE_CASE)) {
                imageDesc.setX(y);
                imageDesc.setY(x - imageDesc.getHeight());

                id = interactionCompetence[j].getId();

                for (Competence competence : combatGameState.getPersonnageAAfficher().getCompetences() ) {
                    if (competence.getId() == id) {
                        competenceAAfficher = competence;
                        imageDesc.setTexte(competenceAAfficher.getNom() + "\n"
                                            + competenceAAfficher.getDescription() + "\n"
                                            + "Actions : " + competence.getCout() + "\n"
                                            + "Dégas : " + competence.getDegaBase() + "\n"
                                            + "Portée : " + competence.getPortee());
                    }
                }
            }
        }


        imageDesc.setVisible(visible);
    }

    public void mouseClicked(int button, int x, int y, int nbClick) {
        int idCase = -1;

        if (!attanteJoueur) {
            // Gestion des déplacement du joueur
            idCase = combatGameState.getTerrain().inCase(x, y);
            combatGameState.deplacement(idCase);

            // Gestion du click sur les competence
            for (int i = 0; i < interactionCompetence.length; i++) {
                if (interactionCompetence[i].inCase(x, y)) {
                    idCompetence = interactionCompetence[i].getId();
                    possiblementUtiliser = combatGameState.action(idCompetence);
                    attanteJoueur = true;
                }
            }

            // Gestion du click sur le bouton tour suivant
            if (btnSuivant.isInBouton(x, y)) {
                combatGameState.tourSuivant();
            }
        } else {
            for (Case caseTerrain : possiblementUtiliser) {
                if (caseTerrain.inCase(x, y)) {
                    idCase = caseTerrain.getId();
                }
            }
            if (idCase != -1) {
                combatGameState.utilisisationCompetence(idCase, idCompetence);
            }
            attanteJoueur = false;
        }
    }

    public void update(PersonnageJoueurClient personnageAAfficher) {
        if (personnageAAfficher.getStats().getVieRestante() >= 0)
            pourcentageVie = (personnageAAfficher.getStats().getVieRestante() * LONGUEUR) / personnageAAfficher.getStats().getVieMax();

        if (personnageAAfficher.getStats().getManaRestante() >= 0)
            pourcentageMana = (personnageAAfficher.getStats().getManaRestante() * LONGUEUR) / personnageAAfficher.getStats().getManaMax();

        if (personnageAAfficher.getStats().getMouvementsRestants() >= 0)
            pourcentageMouvement = (personnageAAfficher.getStats().getMouvementsRestants() * LONGUEUR) / personnageAAfficher.getStats().getMouvementsMax();

        if (personnageAAfficher.getStats().getActionsRestantes() >= 0)
            pourcentageAction = (personnageAAfficher.getStats().getActionsRestantes() * LONGUEUR) / personnageAAfficher.getStats().getActionsMax();
    }
}
