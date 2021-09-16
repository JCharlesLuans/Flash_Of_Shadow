/* FosDAO.java             07/09/2021
 * Copyright et copyleft ThunderBot
 */
package org.thunderbot.FOS.database;

import org.thunderbot.FOS.database.beans.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

            aRetourner.setX(rs.getFloat(PERSONNAGE_X));
            aRetourner.setY(rs.getFloat(PERSONNAGE_Y));

            aRetourner.setIdJoueur(joueur.getId());

            aRetourner.setClasse(getClasseById(rs.getInt(PERSONNAGE_CLE_CLASSE)));

            aRetourner.setMap(getMapById(rs.getInt(PERSONNAGE_CLE_MAP)));

            aRetourner.setStuffArme(getObjetById(rs.getInt(PERSONNAGE_CLE_STUFF_ARME)));
            aRetourner.setStuffBotte(getObjetById(rs.getInt(PERSONNAGE_CLE_STUFF_BOTTE)));
            aRetourner.setStuffGant(getObjetById(rs.getInt(PERSONNAGE_CLE_STUFF_GANT)));
            aRetourner.setStuffJambe(getObjetById(rs.getInt(PERSONNAGE_CLE_STUFF_JAMBE)));
            aRetourner.setStuffTete(getObjetById(rs.getInt(PERSONNAGE_CLE_STUFF_TETE)));
            aRetourner.setStuffTorse(getObjetById(rs.getInt(PERSONNAGE_CLE_STUFF_TORSE)));

            aRetourner.setFaction(getFactionById(rs.getInt(PERSONNAGE_CLE_FACTION)));
            aRetourner.setGuilde(getGuildeById(rs.getInt(PERSONNAGE_CLE_GUILDE)));
            aRetourner.setTitre(getTitreById(rs.getInt(PERSONNAGE_CLE_TITRE)));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return aRetourner;
    }

    public void updatePersonnage(int id, Personnage personnage) {
        String requette =
                "UPDATE " + NOM_TABLE_PERSONNAGE
                + " SET " + PERSONNAGE_X + " = " + personnage.getX() + ','
                          + PERSONNAGE_Y + " = " + personnage.getY() + ','
                          + PERSONNAGE_CLE_MAP + " = " + personnage.getMap().getId() + ','
                          + PERSONNAGE_CLE_STUFF_TETE + " = " + personnage.getStuffTete().getId() + ','
                          + PERSONNAGE_CLE_STUFF_TORSE + " = " + personnage.getStuffTorse().getId() + ','
                          + PERSONNAGE_CLE_STUFF_GANT + " = " + personnage.getStuffGant().getId() + ','
                          + PERSONNAGE_CLE_STUFF_JAMBE + " = " + personnage.getStuffJambe().getId() + ','
                          + PERSONNAGE_CLE_STUFF_BOTTE + " = " + personnage.getStuffBotte().getId() + ','
                          + PERSONNAGE_CLE_STUFF_ARME + " = " + personnage.getStuffArme().getId() + ','
                          + PERSONNAGE_CLE_GUILDE + " = " + personnage.getGuilde().getId() + ','
                          + PERSONNAGE_CLE_TITRE + " = " + personnage.getTitre().getId()
                + " WHERE " + PERSONNAGE_CLE + " = " + id;

        gestionnaireBase.executeUpdate(requette);
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
            aRetourner.setStatDexterite(rs.getInt(CLASSE_STAT_DEXTERITE));
            aRetourner.setStatEndurance(rs.getInt(CLASSE_STAT_ENDURANCE));
            aRetourner.setStatForce(rs.getInt(CLASSE_STAT_FORCE));
            aRetourner.setStatIntelligence(rs.getInt(CLASSE_STAT_INTELLIGENCE));
            aRetourner.setStatSagesse(rs.getInt(CLASSE_STAT_SAGESSE));

            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return aRetourner;
    }

    public ArrayList<Classe> getClasseAll() {
        ArrayList<Classe> aRetourner = new ArrayList<>();
        String requete =
                "SELECT * FROM " + NOM_TABLE_CLASSE;

        try {

            ResultSet rs = gestionnaireBase.executeRequete(requete);
            while (rs.next()) {
                Classe tmp = new Classe();
                tmp.setId(rs.getInt(CLASSE_CLE));
                tmp.setNom(rs.getString(CLASSE_NOM));
                tmp.setStatAgilite(rs.getInt(CLASSE_STAT_AGILITE));
                tmp.setStatArmure(rs.getInt(CLASSE_STAT_ARMURE));
                tmp.setStatDexterite(rs.getInt(CLASSE_STAT_DEXTERITE));
                tmp.setStatEndurance(rs.getInt(CLASSE_STAT_ENDURANCE));
                tmp.setStatForce(rs.getInt(CLASSE_STAT_FORCE));
                tmp.setStatIntelligence(rs.getInt(CLASSE_STAT_INTELLIGENCE));
                tmp.setStatSagesse(rs.getInt(CLASSE_STAT_SAGESSE));
                aRetourner.add(tmp);
            }
            rs.close();

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

    public Map getMapByName(String nom) {
        Map aRetourner = new Map();

        String requete =
                "SELECT * FROM " + NOM_TABLE_MAP + " WHERE " + MAP_NOM + " = '" + nom + "';";

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

        String requete =
                "SELECT * FROM " + NOM_TABLE_OBJET + " WHERE " + OBJET_CLE + " = " + id;

        try {
          ResultSet rs = gestionnaireBase.executeRequete(requete);

          aRetourner.setId(rs.getInt(OBJET_CLE));
          aRetourner.setNom(rs.getString(OBJET_NOM));
          aRetourner.setEmplacement(rs.getInt(OBJET_EMPLACEMENT));
          aRetourner.setStatAgilite(rs.getInt(OBJET_STAT_AGILITE));
          aRetourner.setStatArmure(rs.getInt(OBJET_STAT_ARMURE));
          aRetourner.setStatDexterite(rs.getInt(OBJET_STAT_DEXTERITE));
          aRetourner.setStatEndurance(rs.getInt(OBJET_STAT_ENDURANCE));
          aRetourner.setStatForce(rs.getInt(OBJET_STAT_FORCE));
          aRetourner.setStatIntelligence(rs.getInt(OBJET_STAT_INTELLIGENCE));
          aRetourner.setStatSagesse(rs.getInt(OBJET_STAT_SAGESSE));
          aRetourner.setDps(rs.getInt(OBJET_DPS));
          aRetourner.setImage(rs.getString(OBJET_IMAGE));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return aRetourner;
    }



    public Faction getFactionById(int id) {
        Faction aRetourner = new Faction();
        return aRetourner;
    }

    public ArrayList<Faction> getFactionAll() {
        ArrayList<Faction> aRetourner = new ArrayList<>();
        String requete =
                "SELECT * FROM " + NOM_TABLE_FACTION;

        try {

            ResultSet rs = gestionnaireBase.executeRequete(requete);
            while (rs.next()) {
                Faction tmp = new Faction();
                tmp.setId(rs.getInt(FACTION_CLE));
                tmp.setNom(rs.getString(FACTION_NOM));
                aRetourner.add(tmp);
            }
            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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
