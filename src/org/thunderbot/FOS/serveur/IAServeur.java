package org.thunderbot.FOS.serveur;

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

            int nombreCarte = 0;
            PNJ pnjTmp;

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
            }



            // Boucle infini
            while (true) {
                // Pour toute les maps
                for (int indexMap = 1; indexMap <= nombreCarte; indexMap ++) {

                    // Chargement de la map a simuler en fonction de son nom
                    colisions = Carte.getColisionObject(nomMap[4]);


                    int nombresPNJSurMap = pnjByMap[indexMap].size();

                    for (int indexPNJ = 0; indexPNJ < nombresPNJSurMap; indexPNJ++) {
                        // Simuler les PNJs
                        pnjTmp = (PNJ) pnjByMap[indexMap].get(indexPNJ);

                        pnjTmp.setX(pnjTmp.getX() + VITESSE_PNJ);

                    }
                }
            }


    }

    public ArrayList<PersonnageNonJoueur> getPnjByMap(int i) {
        return pnjByMap[i];
    }
}
