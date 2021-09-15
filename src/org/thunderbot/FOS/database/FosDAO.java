/* FosDAO.java             07/09/2021
 * Copyright et copyleft ThunderBot
 */
package org.thunderbot.FOS.database;

import org.thunderbot.FOS.database.beans.*;

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

    public Personnage getPersonnageByJoueur(Joueur joueur) {
        Personnage aRetourner = new Personnage();

        String requete =
                "SELECT * FROM " + NOM_TABLE_PERSONNAGE + " WHERE " + PERSONNAGE_CLE_JOUEUR +" = " + joueur.getId();

        try {
            ResultSet rs = gestionnaireBase.executeRequete(requete);

            aRetourner.setId(rs.getInt(PERSONNAGE_CLE));
            aRetourner.setNom(rs.getString(PERSONNAGE_NOM));
            aRetourner.setSprite(rs.getString(PERSONNAGE_SPRITE));

            aRetourner.setIdJoueur(joueur.getId());

            aRetourner.setClasse(getClasseById(rs.getInt(PERSONNAGE_CLE_CLASSE)));

            aRetourner.setMap(getMapById(rs.getInt(PERSONNAGE_CLE_MAP)).getNom());

            aRetourner.setStuffArme(getObjetById(rs.getInt(PERSONNAGE_CLE_STUFF_ARME)));
            aRetourner.setStuffBotte(getObjetById(rs.getInt(PERSONNAGE_CLE_STUFF_BOTTE)));
            aRetourner.setStuffGant(getObjetById(rs.getInt(PERSONNAGE_CLE_STUFF_GANT)));
            aRetourner.setStuffJambe(getObjetById(rs.getInt(PERSONNAGE_CLE_STUFF_JAMBE)));
            aRetourner.setStuffTete(getObjetById(rs.getInt(PERSONNAGE_CLE_STUFF_TETE)));
            aRetourner.setStuffTorse(getObjetById(rs.getInt(PERSONNAGE_CLE_STUFF_TORSE)));

            aRetourner.setFaction(getFactionById(rs.getInt(PERSONNAGE_CLE_FACTION)).getNom());
            aRetourner.setGuilde(getGuildeById(rs.getInt(PERSONNAGE_CLE_GUILDE)).getNom());
            aRetourner.setTitre(getTitreById(rs.getInt(PERSONNAGE_CLE_TITRE)).getNom());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return aRetourner;
    }

    public Classe getClasseById(int id) {
        Classe aRetourner = new Classe();
        String requete =
                "SELECT * FROM " + NOM_TABLE_CLASSE + " WHERE " + CLASSE_CLE + " = " + id;

        try {
            ResultSet rs = gestionnaireBase.executeRequete(requete);

            aRetourner.setId(rs.getInt(CLASSE_CLE));
            aRetourner.setNom(rs.getString(CLASSE_NOM));

            aRetourner.setStatAgilite(rs.getInt(CLASSE_STAT_AGILITE));
            aRetourner.setStatArmure(rs.getInt(CLASSE_STAT_ARMURE));
            aRetourner.setStatEndurance(rs.getInt(CLASSE_STAT_ENDURANCE));
            aRetourner.setStatForce(rs.getInt(CLASSE_STAT_FORCE));
            aRetourner.setStatIntelligence(rs.getInt(CLASSE_STAT_INTELLIGENCE));
            aRetourner.setStatSagesse(rs.getInt(CLASSE_STAT_SAGESSE));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return aRetourner;
    }

    public Map getMapById(int id) {
        Map aRetourner = new Map();

        String requete =
                "SELECT * FROM " + NOM_TABLE_MAP + " WHERE " + MAP_CLE + " = " + id;

        try {
            ResultSet rs = gestionnaireBase.executeRequete(requete);

            aRetourner.setId(rs.getInt(MAP_CLE));
            aRetourner.setNom(rs.getString(MAP_NOM));
            aRetourner.setNiveauPNJ(rs.getInt(MAP_NIVEAU_PNJ));
            aRetourner.setNombreMob(rs.getInt(MAP_NOMBRE_MOB));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return aRetourner;
    }

    public Objet getObjetById(int id) {
        Objet aRetourner = new Objet();
        return aRetourner;
    }

    public Faction getFactionById(int id) {
        Faction aRetourner = new Faction();
        return aRetourner;
    }

    public Titre getTitreById(int id) {
        Titre aRetourner = new Titre();
        return aRetourner;
    }

    public Guilde getGuildeById(int id) {
        Guilde aRetourner = new Guilde();
        return aRetourner;
    }

}
