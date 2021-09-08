/* FosDAO.java             07/09/2021
 * Copyright et copyleft ThunderBot
 */
package org.thunderbot.FOS.database;

import org.thunderbot.FOS.database.beans.Joueur;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.thunderbot.FOS.database.HelperBD.*;

/**
 * Acces au objet de la BD
 */
public class FosDAO {

    public static final String NOM_BD = "fos.db";

    HelperBD gestionnaireBase;

    public FosDAO() {
        gestionnaireBase = new HelperBD(NOM_BD);
    }

    public Joueur getJoueurByPseudo(String pseudo) {
        Joueur aRetourner = new Joueur();

        String requete =
                " SELECT * " +
                " FROM " + HelperBD.NOM_TABLE_JOUEUR +
                " WHERE " + HelperBD.JOUEUR_PSEUDO + " = '" + pseudo + "';";
        try {

            ResultSet rs = gestionnaireBase.executeRequete(requete);

            aRetourner.setExistant(!rs.isClosed());

            if (aRetourner.isExistant()) {
                aRetourner.setId(rs.getInt(JOUEUR_CLE));
                aRetourner.setPseudo(rs.getString(JOUEUR_PSEUDO));
                aRetourner.setMdp(rs.getString(JOUEUR_MDP));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return aRetourner;
    }

    public void addJoueur(Joueur joueur) {
        String requete =
                "INSERT INTO JOUEUR (" + JOUEUR_PSEUDO + ","+ JOUEUR_MDP + ")"
                + "VALUES           ('" + joueur.getPseudo() + "','" + joueur.getMdp() +"');";

        gestionnaireBase.executeUpdate(requete);
    }
}
