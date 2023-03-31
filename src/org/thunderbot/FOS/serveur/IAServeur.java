package org.thunderbot.FOS.serveur;

import org.thunderbot.FOS.client.gameState.entite.Personnage;
import org.thunderbot.FOS.client.gameState.entite.PersonnageNonJoueur;
import org.thunderbot.FOS.client.gameState.world.Carte;
import org.thunderbot.FOS.database.FosDAO;
import org.thunderbot.FOS.database.beans.PNJ;

import java.util.ArrayList;

/**
 * Gere la  gestion de la simulation de l'inteligence articificielle de déplacement des mob agressifs.
 * Gere de maniere procédurale par map. Accede a la BD, obtiens le nombre de map, le nombre de PNJ par map.
 */
public class IAServeur extends Thread {

    final static float VITESSE_PNJ = 0.050f;

    private FosDAO accesBD;


    private ArrayList[] pnjByMap;

    public IAServeur(FosDAO accesBD) {
        this.accesBD = accesBD;
    }

    public void run() {

            String[] nomMap;    // Tableau associant le nom et l'id de la map

            int[][] colisions;
            int xColision        = 0;
            int yColision        = 0;
            int hauteurColision  = 0;
            int longueurColision = 0;
            boolean peutPasMarcher = false;

            int nombreCarte = 0;
            PNJ pnjTmp;

            float timer = 0;



            // Démarage du thread
            // Obtenir le nombre de carte

            nombreCarte = accesBD.getNombreMap();

            // Initialisation du tableau des PNJ
            pnjByMap = new ArrayList[nombreCarte + 1];
            nomMap = new String[nombreCarte + 1];

            // POur chaque carte, on va chercher les PNJ qui sont dessus
            for (int indexMap = 1; indexMap <= nombreCarte; indexMap ++) {
                pnjByMap[indexMap] = accesBD.getPnjByIdMap("" + indexMap);
                nomMap[indexMap] = accesBD.getMapById(indexMap).getNom();

                  // Pour toute les map et pour tout les PNJ initialiser une direction par defaut
                for (int indexPNJ = 0; indexPNJ < pnjByMap[indexMap].size(); indexPNJ++) {
                    pnjTmp = (PNJ) pnjByMap[indexMap].get(indexPNJ);
                    pnjTmp.setDirection(0);
                }
            }



            // Boucle infini de fonctionnement
            while (true) {

                timer++;

                // Pour toute les maps
                for (int indexMap = 1; indexMap <= nombreCarte; indexMap ++) {

                    // Chargement de la map a simuler en fonction de son nom
                    colisions = Carte.getColisionObject(nomMap[4]);

                    int nombresPNJSurMap = pnjByMap[indexMap].size();

                    // Tester tout les PNJ sur la map
                    for (int indexPNJ = 0; indexPNJ < nombresPNJSurMap; indexPNJ++) {

                        // Simuler les PNJs
                        pnjTmp = (PNJ) pnjByMap[indexMap].get(indexPNJ);

                        // Change la direction d'un PNJ de maniere léatoire
                        if (timer > 1000f) {
                            pnjTmp.setDirection(Personnage.HAUT + (int) (Math.random() * ((Personnage.DROITE - Personnage.HAUT) + 1)));
                            System.out.println("Timer");
                            timer = 0;
                        }

                        // Possible d'aller la ? => gestion colision
                        // Test de tutes les colisions
                        for (int indexColision = 0; indexColision < colisions.length; indexColision++) {
                            // Recuperation des coordonnée de la colision :
                            // Premier index => ID de la colisions donc inutiles
                            xColision        = colisions[indexColision][1];
                            yColision        = colisions[indexColision][2];
                            longueurColision = colisions[indexColision][3];
                            hauteurColision  = colisions[indexColision][4];

                            //    Si le x + vitesse n'est pas compris dans xColision et xColision + longueurColision
                            // Ou si le Y + vitesse n'est pas compris dans yColision et yColision + hauteurColision
                            // alors on peut marcher

                            // RAZ des colisions
                            peutPasMarcher = false;

                            switch (pnjTmp.getDirection()) {
                                case Personnage.HAUT:
                                    peutPasMarcher = (yColision <= pnjTmp.getY() - VITESSE_PNJ && pnjTmp.getY() - VITESSE_PNJ <= yColision + hauteurColision) && (xColision <= pnjTmp.getX() && pnjTmp.getX() <= xColision + longueurColision);
                                    break;
                                case Personnage.BAS:
                                    peutPasMarcher = (yColision <= pnjTmp.getY() + VITESSE_PNJ && pnjTmp.getY() + VITESSE_PNJ <= yColision + hauteurColision) && (xColision <= pnjTmp.getX() && pnjTmp.getX() <= xColision + longueurColision);
                                    break;
                                case Personnage.GAUCHE:
                                    peutPasMarcher = (xColision <= pnjTmp.getX() + VITESSE_PNJ && pnjTmp.getX() + VITESSE_PNJ <= xColision + longueurColision) && (yColision <= pnjTmp.getY() && pnjTmp.getY() <= yColision + hauteurColision);
                                    break;
                                case Personnage.DROITE:
                                    peutPasMarcher = (xColision <= pnjTmp.getX() - VITESSE_PNJ && pnjTmp.getX() - VITESSE_PNJ <= xColision + longueurColision) && (yColision <= pnjTmp.getY() && pnjTmp.getY() <= yColision + hauteurColision);
                                    break;
                            }

                            if (peutPasMarcher) {
                                System.out.println("Colision"); // DEBUG);
                                break;
                            }

                        }

                        if (!peutPasMarcher) {
                            //=> Oui deplacement
                            switch (pnjTmp.getDirection()) {
                                case Personnage.HAUT:
                                    pnjTmp.setY(pnjTmp.getY() - VITESSE_PNJ);
                                    break;
                                case Personnage.BAS:
                                    pnjTmp.setY(pnjTmp.getY() + VITESSE_PNJ);
                                    break;
                                case Personnage.GAUCHE:
                                    pnjTmp.setX(pnjTmp.getX() + VITESSE_PNJ);
                                    break;
                                case Personnage.DROITE:
                                    pnjTmp.setX(pnjTmp.getX() - VITESSE_PNJ);
                                    break;
                            }
                        } else {
                            //=> Choix d'une direction aléatoire
                            pnjTmp.setDirection(Personnage.HAUT + (int) (Math.random() * ((Personnage.DROITE - Personnage.HAUT) + 1)));
                            System.out.println(pnjTmp.getDirection());
                        }

                    }
                }
            }


    }

    public ArrayList<PersonnageNonJoueur> getPnjByMap(int i) {
        return pnjByMap[i];
    }
}
