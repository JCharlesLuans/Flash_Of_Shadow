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

    public void deleteJoueur(int idJoueur) {
        String requete = "DELETE FROM " + NOM_TABLE_JOUEUR + " WHERE " + JOUEUR_CLE + " = " + idJoueur;
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

            aRetourner.setListeCompetence(this.getCompetenceByPersonnage(rs.getInt(PERSONNAGE_CLE)));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return aRetourner;
    }

    /**
     * Met a jour un personnage dans la base de donnée
     * @param personnage
     */
    public void updatePersonnage(Personnage personnage) {
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
                + " WHERE " + PERSONNAGE_CLE + " = " + personnage.getId();

        gestionnaireBase.executeUpdate(requette);
    }

    public int addPersonnage(Personnage personnage)  {
        int aRetourner = 0;

        String requete =
                "INSERT INTO " + NOM_TABLE_PERSONNAGE
                        + " ( " + PERSONNAGE_NOM + ", "
                        + PERSONNAGE_SPRITE + ", "
                        + PERSONNAGE_X + ", "
                        + PERSONNAGE_Y + ", "
                        + PERSONNAGE_CLE_JOUEUR + ", "
                        + PERSONNAGE_CLE_CLASSE + ", "
                        + PERSONNAGE_CLE_MAP + ", "
                        + PERSONNAGE_CLE_STUFF_TETE + ", "
                        + PERSONNAGE_CLE_STUFF_TORSE + ", "
                        + PERSONNAGE_CLE_STUFF_GANT + ", "
                        + PERSONNAGE_CLE_STUFF_JAMBE + ", "
                        + PERSONNAGE_CLE_STUFF_BOTTE + ", "
                        + PERSONNAGE_CLE_STUFF_ARME + ", "
                        + PERSONNAGE_CLE_FACTION + ", "
                        + PERSONNAGE_CLE_GUILDE + ", "
                        + PERSONNAGE_CLE_TITRE +  ")"
                + "VALUES ( '"
                        + personnage.getNom() + "', '"
                        + personnage.getSprite() + "', "
                        + personnage.getX() + ", "
                        + personnage.getY() + ", "
                        + personnage.getIdJoueur() + ", "
                        + personnage.getClasse().getId() + ", "
                        + personnage.getMap().getId() + ", "
                        + personnage.getStuffTete().getId() + ", "
                        + personnage.getStuffTorse().getId() + ", "
                        + personnage.getStuffGant().getId() + ", "
                        + personnage.getStuffJambe().getId() + ", "
                        + personnage.getStuffBotte().getId() + ", "
                        + personnage.getStuffArme().getId() + ", "
                        + personnage.getFaction().getId() + ", "
                        + personnage.getGuilde().getId() + ", "
                        + personnage.getTitre().getId() + "); ";


        gestionnaireBase.executeUpdate(requete);
        try {
            ResultSet resultSet = gestionnaireBase.executeRequete("SELECT COUNT(*) FROM " + NOM_TABLE_PERSONNAGE);
            aRetourner = resultSet.getInt(1);
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return aRetourner;
    }


    public Competence getCompetenceById(int id) {
        Competence aRetourner = new Competence();
        String requete =
                "SELECT * FROM " + NOM_TABLE_COMPETENCE + " WHERE " + COMPETENCE_CLE + " = " + id;

        try {
            ResultSet rs = gestionnaireBase.executeRequete(requete);

            aRetourner.setId(rs.getInt(COMPETENCE_NOM));
            aRetourner.setNom(rs.getString(COMPETENCE_NOM));
            aRetourner.setDegaBase(rs.getInt(COMPETENCE_DEGAT_BASE));
            aRetourner.setPortee(rs.getInt(COMPETENCE_PORTEE));
            aRetourner.setCout(rs.getInt(COMPETENCE_COUT));
            aRetourner.setIdEffet(rs.getInt(COMPETENCE_ID_EFFET));
            aRetourner.setImage(rs.getString(COMPETENCE_IMAGE));


            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return aRetourner;
    }

    /**
     * Recupére la liste de compétance d'un personnage dont l'id est passer en paramettre
     * @param idPersonnage dont on veux les compétance
     * @return la liste des compétance sous forme d'une array list
     */
    public ArrayList<Competence> getCompetenceByPersonnage(int idPersonnage) {
        ArrayList<Competence> aRetourner = new ArrayList<>();
        String requete =
                "SELECT *" +
                " FROM " + NOM_TABLE_COMPETENCE +
                " JOIN " + NOM_TABLE_LISTE_COMPETENCE +
                " ON " + LISTE_COMPETENCE_CLE_COMPETENCE + " = " + COMPETENCE_CLE +
                " WHERE " + LISTE_COMPETENCE_CLE_PERSO + " = " + idPersonnage  + ";";
        try {

            ResultSet rs = gestionnaireBase.executeRequete(requete);
            while (rs.next()) {
                Competence tmp = new Competence();
                tmp.setId(rs.getInt(COMPETENCE_CLE));
                tmp.setNom(rs.getString(COMPETENCE_NOM));
                tmp.setDegaBase(rs.getInt(COMPETENCE_DEGAT_BASE));
                tmp.setPortee(rs.getInt(COMPETENCE_PORTEE));
                tmp.setCout(rs.getInt(COMPETENCE_COUT));
                tmp.setIdEffet(rs.getInt(COMPETENCE_ID_EFFET));
                tmp.setImage(rs.getString(COMPETENCE_IMAGE));

                aRetourner.add(tmp);
            }
            rs.close();

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

    public Classe getClasseByName(String nom) {
        Classe aRetourner = new Classe();

        String requete =
                "SELECT * FROM " + NOM_TABLE_CLASSE + " WHERE " + CLASSE_NOM + " = '" + nom + "';";

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

    public int getNombreMap() {
        int aRetourner = 0;

        String requete =
                "SELECT COUNT(*) FROM " + NOM_TABLE_MAP + ";" ;

        try {
            ResultSet rs = gestionnaireBase.executeRequete(requete);

            aRetourner = rs.getInt(1);

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
          aRetourner.setDesc(rs.getString(OBJET_DESC));
          aRetourner.setEquipableParClasse(rs.getInt(OBJET_ID_CLASSE));
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

          rs.close();

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

    public Faction getFactionByName(String nom) {
        Faction aRetourner = new Faction();
        String requete =
                "SELECT * FROM " + NOM_TABLE_FACTION + " WHERE " + FACTION_NOM + " = '" + nom + "';";

        try {
            ResultSet resultSet = gestionnaireBase.executeRequete(requete);

            aRetourner.setId(resultSet.getInt(FACTION_CLE));
            aRetourner.setNom(resultSet.getString(FACTION_NOM));
            aRetourner.setMapStart(getMapById(resultSet.getInt(FACTION_CLE_MAP_START)));

            resultSet.close();
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



    public ArrayList<PNJ> getPnjByIdMap(String id) {
        ArrayList<PNJ> aRetourner = new ArrayList<>();
        String requete =
                "SELECT * FROM " + NOM_TABLE_PNJ + " WHERE " + PNJ_CLE_MAP + " = " + id + ";";
        try {

            ResultSet rs = gestionnaireBase.executeRequete(requete);
            while (rs.next()) {
                PNJ tmp = new PNJ();
                tmp.setId(rs.getInt(PNJ_CLE));
                tmp.setNom(rs.getString(PNJ_NOM));
                tmp.setSprite(rs.getString(PNJ_SPRITE));
                tmp.setAgressif(rs.getInt(PNJ_AGRESSIF));
                tmp.setX(rs.getFloat(PNJ_X));
                tmp.setY(rs.getFloat(PNJ_Y));
                tmp.setStatArmure(rs.getInt(PNJ_STAT_ARMURE));
                tmp.setStatAgilite(rs.getInt(PNJ_STAT_AGILITE));
                tmp.setStatDexterite(rs.getInt(PNJ_STAT_DEXTERITE));
                tmp.setStatEndurance(rs.getInt(PNJ_STAT_ENDURANCE));
                tmp.setStatForce(rs.getInt(PNJ_STAT_FORCE));
                tmp.setStatInteligence(rs.getInt(PNJ_STAT_INTELLIGENCE));
                tmp.setStatSagesse(rs.getInt(PNJ_STAT_SAGESSE));
                tmp.setIdMap(rs.getInt(PNJ_CLE_MAP));
                tmp.setIdFaction(rs.getInt(PNJ_CLE_FACTION));
                tmp.setIdTitre(rs.getInt(PNJ_CLE_TITRE));
                aRetourner.add(tmp);
            }
            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return aRetourner;
    }

    public ArrayList<PNJ> getPnjAgressifByIdMap(String idMap, String agressif ) {
        ArrayList<PNJ> aRetourner = new ArrayList<>();
        String requete =
                "SELECT * FROM " + NOM_TABLE_PNJ + " WHERE " + PNJ_CLE_MAP + " = " + idMap + " AND " + PNJ_AGRESSIF +" = " + agressif + ";";
        try {

            ResultSet rs = gestionnaireBase.executeRequete(requete);
            while (rs.next()) {
                PNJ tmp = new PNJ();
                tmp.setId(rs.getInt(PNJ_CLE));
                tmp.setNom(rs.getString(PNJ_NOM));
                tmp.setSprite(rs.getString(PNJ_SPRITE));
                tmp.setAgressif(rs.getInt(PNJ_AGRESSIF));
                tmp.setX(rs.getFloat(PNJ_X));
                tmp.setY(rs.getFloat(PNJ_Y));
                tmp.setStatArmure(rs.getInt(PNJ_STAT_ARMURE));
                tmp.setStatAgilite(rs.getInt(PNJ_STAT_AGILITE));
                tmp.setStatDexterite(rs.getInt(PNJ_STAT_DEXTERITE));
                tmp.setStatEndurance(rs.getInt(PNJ_STAT_ENDURANCE));
                tmp.setStatForce(rs.getInt(PNJ_STAT_FORCE));
                tmp.setStatInteligence(rs.getInt(PNJ_STAT_INTELLIGENCE));
                tmp.setStatSagesse(rs.getInt(PNJ_STAT_SAGESSE));
                tmp.setIdMap(rs.getInt(PNJ_CLE_MAP));
                tmp.setIdFaction(rs.getInt(PNJ_CLE_FACTION));
                tmp.setIdTitre(rs.getInt(PNJ_CLE_TITRE));
                aRetourner.add(tmp);
            }
            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return aRetourner;
    }

    public ArrayList<Competence> getCompetenceAll() {
        ArrayList<Competence> aRetourner = new ArrayList<>();
        String requete =
                "SELECT * FROM " + NOM_TABLE_COMPETENCE + ";";
        try {

            ResultSet rs = gestionnaireBase.executeRequete(requete);
            while (rs.next()) {
                Competence tmp = new Competence();
                tmp.setId(rs.getInt(COMPETENCE_CLE));
                tmp.setNom(rs.getString(COMPETENCE_NOM));
                tmp.setDegaBase(rs.getInt(COMPETENCE_DEGAT_BASE));
                tmp.setIdEffet(rs.getInt(COMPETENCE_ID_EFFET));
                tmp.setImage(rs.getString(COMPETENCE_IMAGE));
                aRetourner.add(tmp);
            }
            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return aRetourner;
    }

    /**
     * Ajoute une nouvelle entrée dans la table liste compétence, ce qui permet de faire le liens entre la competence et
     * le personnage dont les ID sont passer en paramettre
     * @param idPersonnage
     * @param idCompetence
     */
    public void addListeCompetence(int idPersonnage, int idCompetence) {
        String requete =
                "INSERT INTO " + NOM_TABLE_LISTE_COMPETENCE
                        + " ( " + LISTE_COMPETENCE_EXPERIENCE + ", "
                        + LISTE_COMPETENCE_CLE_COMPETENCE + ", "
                        + LISTE_COMPETENCE_CLE_PERSO + ")"
                        + "VALUES ( '"
                        + 0 + "', '"
                        + idCompetence + "', "
                        + idPersonnage + "); ";


        gestionnaireBase.executeUpdate(requete);
    }

    public HelperBD getGestionnaireBase() {
        return gestionnaireBase;
    }


}
