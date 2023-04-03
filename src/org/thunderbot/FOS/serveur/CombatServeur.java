package org.thunderbot.FOS.serveur;

import org.thunderbot.FOS.client.ChaosRevolt;
import org.thunderbot.FOS.client.combatState.CombatGameState;
import org.thunderbot.FOS.client.gameState.entite.PersonnageNonJoueur;
import org.thunderbot.FOS.database.FosDAO;
import org.thunderbot.FOS.database.beans.PNJ;
import org.thunderbot.FOS.database.beans.Personnage;

import java.util.ArrayList;
import java.util.Random;

/**
 * Gestion des combats depuis le serveur
 */
public class CombatServeur {

    private Serveur serveur;

    private FosDAO accesBD;

    private int nombreCaseLongueur;
    private int nombreCaseHauteur;

    private ArrayList<PNJ> listePnjEnCombat;
    Random rnd;

    public CombatServeur(Serveur serveur, FosDAO accesBD) {
        this.serveur = serveur;
        this.accesBD = accesBD;

        nombreCaseLongueur = ChaosRevolt.WIDTH / CombatGameState.TAILLE_CASE;
        nombreCaseHauteur  = (ChaosRevolt.HEIGHT / CombatGameState.TAILLE_CASE) - CombatGameState.TAILLE_INTERFACE;

        rnd = new Random();
    }

    /**
     * Initialise les PNJ pour le combat.
     * En fonction du PNJ reçu (le PNJ reçu est le PNJ sur lequelle clique le joueur) on charge entre 2 et 4 autre PNJ.
     * On genere une position aléatoire
     * Puis on les revois au joueur.
     * @return
     */
    private void initCombatPnj() {

        ArrayList<PNJ> listePnjPossible;
        listePnjEnCombat = new ArrayList<>();

        PNJ pnjTemporaire;

        int nombrePNJ = 0;

        // Donnée pour la simulation du terrain


        boolean dejaUtilise;

        // Attente de la reception des infos complementaire => PNJ
        pnjTemporaire = (PNJ) serveur.receptionXML();

        // Génération du nombre de PNJ que va affronter le joueur (entre 2 et 4)
        nombrePNJ = 2 + rnd.nextInt(3);

        // Recuperation des PNJ possibles sur la maps
        listePnjPossible = accesBD.getPnjAgressifByIdMap(pnjTemporaire.getIdMap() + "", "1");

        // Pour tout le nombres de PNJ possible, choisir aléatoirement les pnj dans la liste créer précédament
        for (int i = 0; i < nombrePNJ; i++) {
            listePnjEnCombat.add(new PNJ(listePnjPossible.get(rnd.nextInt(listePnjPossible.size()))));
        }

        // Une fois tout les PNJ choisis, selectionner leurs position de maniere aléatoire
        for (int i = 0; i < listePnjEnCombat.size(); i++) {

            listePnjEnCombat.get(i).setDirection(org.thunderbot.FOS.client.gameState.entite.Personnage.BAS);

            dejaUtilise = false; // Reinitialise l'indicateur de coordonnée deja utiliser

            do {
                // Choisi un position aléatoire sur la carte, puis applique un delta pour l'affichage
                listePnjEnCombat.get(i).setX((rnd.nextInt(nombreCaseLongueur) + 1) * CombatGameState.TAILLE_CASE - 32);
                listePnjEnCombat.get(i).setY((rnd.nextInt(nombreCaseHauteur) + 1) * CombatGameState.TAILLE_CASE - 16);

                for (int j = 0; j < listePnjEnCombat.size(); j++) {
                    if (j != i) {
                        dejaUtilise = (listePnjEnCombat.get(i).getX() == listePnjEnCombat.get(j).getX()
                                && listePnjEnCombat.get(i).getY() == listePnjEnCombat.get(j).getY())
                                || dejaUtilise;
                    }
                }
            } while (dejaUtilise);
        }

        // Renvoi de la liste au joueur
        serveur.envoiXML(listePnjEnCombat);
    }

    private void initJoueurCombat() {

        boolean dejaUtilise;

        // Reception du personnage client
        Personnage personnage = (Personnage) serveur.receptionXML();

        // Sauvegarde en BD
        accesBD.updatePersonnage(personnage);

        // Gestion de la position du personnage sur le Combat Game State
        dejaUtilise = false; // Reinitialise l'indicateur de coordonnée deja utiliser

        do {
            // Choisi un position aléatoire sur la carte, puis applique un delta pour l'affichage
            personnage.setX((rnd.nextInt(nombreCaseLongueur) + 1) * CombatGameState.TAILLE_CASE - 32);
            personnage.setY((rnd.nextInt(nombreCaseHauteur)  + 1) * CombatGameState.TAILLE_CASE - 16);

            for (int j = 0; j < listePnjEnCombat.size(); j++) {
                    dejaUtilise = (personnage.getX() == listePnjEnCombat.get(j).getX()
                            && personnage.getY() == listePnjEnCombat.get(j).getY())
                            || dejaUtilise;
            }
        } while (dejaUtilise);

        serveur.envoiXML(personnage);
    }

    public void update() {
        // reception du joueur
        serveur.receptionXML();

        // reception etat des pnj
        serveur.receptionXML();

        // envoi nouvelle position pnj
        serveur.envoiXML(listePnjEnCombat);

        // envoi etat joueur
        serveur.envoiXML(new Personnage());
    }

    public void start() {
        initCombatPnj();
        initJoueurCombat();
    }

    public void end() {
        System.out.println("Fin de combat");

        // reception du joueur
        Personnage personnage = (Personnage) serveur.receptionXML();

        // reception etat des pnj
        serveur.receptionXML();

        personnage.setMap(accesBD.getMapByName("map_cimetiere.tmx"));
        personnage.setX(910);
        personnage.setY(500);

        System.out.println(personnage);

        // envoi etat joueur
        serveur.envoiXML(personnage);

    }
}
