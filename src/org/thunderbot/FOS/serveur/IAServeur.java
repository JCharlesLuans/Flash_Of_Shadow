package org.thunderbot.FOS.serveur;

import org.thunderbot.FOS.client.gameState.entite.PersonnageNonJoueur;
import org.thunderbot.FOS.database.FosDAO;
import org.thunderbot.FOS.database.HelperBD;
import org.thunderbot.FOS.database.beans.PNJ;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Gere la  gestion de la simulation de l'inteligence articificielle de déplacement des mob agressifs.
 * Gere de maniere procédurale par map. Accede a la BD, obtiens le nombre de map, le nombre de PNJ par map.
 */
public class IAServeur extends Thread {

    private FosDAO accesBD;

    private ArrayList[] pnjByMap;

    public IAServeur(FosDAO accesBD) {
        this.accesBD = accesBD;
    }

    public void run() {
        int nombreCarte = 0;


        // Démarage du thread
        // Acces a la BD
        HelperBD helper = accesBD.getGestionnaireBase();

        // Obtenir le nombre de carte
        String requete = "SELECT COUNT(*) FROM Map";
        ResultSet rs = helper.executeRequete(requete);

        try {
                if (rs != null) {
                    nombreCarte = rs.getInt(1);
                }

                // Initialisation du tableau des PNJ
                pnjByMap = new ArrayList[nombreCarte + 1];

                // POur chaque carte, on va chercher les PNJ qui sont dessus
                for (int indexMap = 1; indexMap <= nombreCarte; indexMap ++) {
                    pnjByMap[indexMap] = accesBD.getPnjByIdMap("" + indexMap);
                }

                while (true) {
                    for (int indexMap = 1; indexMap <= nombreCarte; indexMap ++) {

                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
        }

        // Boucle infini
            // Simuler le deplacement des PNJ
            // Enregistre la simulation des PNJ dans un objet accessible depuis les thread des client pour leurs envoyers
    }

    public ArrayList<PersonnageNonJoueur> getPnjByMap(int i) {
        return pnjByMap[i];
    }
}
