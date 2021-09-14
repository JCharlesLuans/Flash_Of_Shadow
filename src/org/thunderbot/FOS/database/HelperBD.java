/* HelperBdDAO.java             07/09/2021
 * Copyright et copyleft ThunderBot
 */
package org.thunderbot.FOS.database;

import java.sql.*;

/**
 * Acces au table de la BD
 */
public class HelperBD {

    ///// TABLE JOUEUR ///////
    public static final String NOM_TABLE_JOUEUR = "Joueur";
    public static final String JOUEUR_CLE = "_id";
    public static final String JOUEUR_PSEUDO = "pseudo";
    public static final String JOUEUR_MDP = "mot_de_passe";

    private static final String CREATION_TABLE_JOUEUR =
            "CREATE TABLE " + NOM_TABLE_JOUEUR + " ( "
                 + JOUEUR_CLE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                 + JOUEUR_PSEUDO + " TEXT NOT NULL,"
                 + JOUEUR_MDP + " TEXT NOT NULL "
                 + " );" ;


    ///// TABLE CLASSE ///////
    public static final String NOM_TABLE_CLASSE = "Classe";
    public static final String CLASSE_CLE = "_id";
    public static final String CLASSE_NOM = "nom";
    public static final String CLASSE_STAT_ARMURE = "statArmure";
    public static final String CLASSE_STAT_FORCE = "statForce";
    public static final String CLASSE_STAT_INTELLIGENCE = "statIntelligence";
    public static final String CLASSE_STAT_AGILITE = "statAgilite";
    public static final String CLASSE_STAT_ENDURANCE = "statEndurence";
    public static final String CLASSE_STAT_SAGESSE = "statSagesse";

    private static final String CREATION_TABLE_CLASSE =
            "CREATE TABLE " + NOM_TABLE_CLASSE + " ( "
            + CLASSE_CLE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CLASSE_NOM + " TEXT NOT NULL,"
            + CLASSE_STAT_ARMURE + " INTEGER NOT NULL,"
            + CLASSE_STAT_FORCE + " INTEGER NOT NULL,"
            + CLASSE_STAT_INTELLIGENCE + " INTEGER NOT NULL,"
            + CLASSE_STAT_AGILITE + " INTEGER NOT NULL,"
            + CLASSE_STAT_ENDURANCE + "INTEGER NOT NULL,"
            + CLASSE_STAT_SAGESSE + " INTEGER NOT NULL"
            + ");";


    ///// TABLE EFFET /////////////
    public static final String NOM_TABLE_EFFET = "Effet";
    public static final String EFFET_CLE = "_id";
    public static final String EFFET_NOM = "nom";
    public static final String EFFET_STAT_ARMURE = "statArmure";
    public static final String EFFET_STAT_FORCE = "statForce";
    public static final String EFFET_STAT_INTELLIGENCE = "statIntelligence";
    public static final String EFFET_STAT_AGILITE = "statAgilite";
    public static final String EFFET_STAT_ENDURANCE = "statEndurence";
    public static final String EFFET_STAT_SAGESSE = "statSagesse";
    public static final String EFFET_DPS = "dps";
    public static final String EFFET_DUREE = "duree";
    public static final String EFFET_ID_IMAGE = "idImage";

    private static final String CREATION_TABLE_EFFET =
            "CREATE TABLE " + NOM_TABLE_EFFET + " ( "
                + EFFET_CLE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EFFET_NOM + " TEXT NOT NULL,"
                + EFFET_STAT_ARMURE       + " INTEGER NOT NULL,"
                + EFFET_STAT_FORCE        + " INTEGER NOT NULL,"
                + EFFET_STAT_INTELLIGENCE + " INTEGER NOT NULL,"
                + EFFET_STAT_AGILITE      + " INTEGER NOT NULL,"
                + EFFET_STAT_ENDURANCE    + " INTEGER NOT NULL,"
                + EFFET_STAT_SAGESSE      + " INTEGER NOT NULL,"
                + EFFET_DPS + " INTEGER NOT NULL,"
                + EFFET_DUREE +  " INTEGER NOT NULL,"
                + EFFET_ID_IMAGE + "INTEGER NOT NULL"
            + ");";

    ///// TABLE COMPETENCE ////////
    public static final String NOM_TABLE_COMPETENCE = "Competence";
    public static final String COMPETENCE_CLE = "_id";
    public static final String COMPETENCE_NOM = "nom";
    public static final String COMPETENCE_DEGAT_BASE = "degatBase";
    public static final String COMPETENCE_ID_EFFET = "idEffet";
    public static final String COMPETENCE_ID_IMAGE = "idImage";

    private static final String CREATION_TABLE_COMPETENCE =
            "CREATE TABLE " + NOM_TABLE_COMPETENCE + " ( "
                + COMPETENCE_CLE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COMPETENCE_NOM + " TEXT NOT NULL,"
                + COMPETENCE_DEGAT_BASE + " INTEGER NOT NULL,"
                + COMPETENCE_ID_EFFET + "INTEGER NOT NULL REFERENCES " + NOM_TABLE_EFFET + " (" + EFFET_CLE + "),"
                + COMPETENCE_ID_IMAGE + "INTEGER NOT NULL"
            + ") ;";


    ///// TABLE MAP //////////////
    public static final String NOM_TABLE_MAP = "Map";
    public static final String MAP_CLE = "_id";
    public static final String MAP_NOM = "nom";
    public static final String MAP_NIVEAU_PNJ = "niveauPNJ";
    public static final String MAP_NOMBRE_MOB = "nombreMob";

    private static final String CREATION_TABLE_MAP =
            "CREATE TABLE " + NOM_TABLE_MAP + " ( "
                + MAP_CLE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MAP_NOM + " TEXT NOT NULL,"
                + MAP_NIVEAU_PNJ + " INTEGER NOT NULL,"
                + MAP_NOMBRE_MOB + " INTEGER NOT NULL"
            + ");";


    ///// TABLE OBJET /////////////
    public static final String NOM_TABLE_OBJET = "Objet";
    public static final String OBJET_CLE = "_id";
    public static final String OBJET_NOM = "nom";
    public static final String OBJET_EMPLACEMENT = "emplacement";
    public static final String OBJET_STAT_ARMURE = "statArmure";
    public static final String OBJET_STAT_FORCE = "statForce";
    public static final String OBJET_STAT_INTELLIGENCE = "statIntelligence";
    public static final String OBJET_STAT_AGILITE = "statAgilite";
    public static final String OBJET_STAT_ENDURANCE = "statEndurence";
    public static final String OBJET_STAT_SAGESSE = "statSagesse";
    public static final String OBJET_DPS = "dps";
    public static final String OBJET_ID_IMAGE = "idImage";

    private static final String CREATION_TABLE_OBJET =
            "CREATE TABLE " + NOM_TABLE_OBJET + " ("
                + OBJET_CLE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + OBJET_NOM + " TEXT NOT NULL,"
                + OBJET_EMPLACEMENT + " INTEGER NOT NULL,"
                + OBJET_STAT_ARMURE       + " INTEGER NOT NULL,"
                + OBJET_STAT_FORCE        + " INTEGER NOT NULL,"
                + OBJET_STAT_INTELLIGENCE + " INTEGER NOT NULL,"
                + OBJET_STAT_AGILITE      + " INTEGER NOT NULL,"
                + OBJET_STAT_ENDURANCE    + " INTEGER NOT NULL,"
                + OBJET_STAT_SAGESSE      + " INTEGER NOT NULL,"
                + OBJET_DPS + " INTEGER NOT NULL,"
                + OBJET_ID_IMAGE + " INTEGER NOT NULL"
            + ");";


    ///// TABLE FACTION    ////////
    public static final String NOM_TABLE_FACTION = "Faction";
    public static final String FACTION_CLE = "_id";
    public static final String FACTION_NOM = "nom";

    private static final String CREATION_TABLE_FACTION =
            "CREATE TABLE " + NOM_TABLE_FACTION + " ( "
                + FACTION_CLE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FACTION_NOM + " TEXT NOT NULL"
            + ");";


    ///// TABLE GUILDE    ////////
    public static final String NOM_TABLE_GUILDE = "Guilde";
    public static final String GUILDE_CLE = "_id";
    public static final String GUILDE_NOM = "nom";

    private static final String CREATION_TABLE_GUILDE =
            "CREATE TABLE " + NOM_TABLE_GUILDE + " ( "
                    + GUILDE_CLE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + GUILDE_NOM + " TEXT NOT NULL"
                    + ");";


    ///// TABLE TITRE   ////////
    public static final String NOM_TABLE_TITRE = "Titre";
    public static final String TITRE_CLE = "_id";
    public static final String TITRE_NOM = "nom";

    private static final String CREATION_TABLE_TITRE =
            "CREATE TABLE " + NOM_TABLE_TITRE + " ( "
                    + TITRE_CLE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + TITRE_NOM + " TEXT NOT NULL"
                    + ");";


    ///// TABLE PERSONNAGE ///////
    public static final String NOM_TABLE_PERSONNAGE = "Personnage";
    public static final String PERSONNAGE_CLE = "_id";
    public static final String PERSONNAGE_NOM = "nom";
    public static final String PERSONNAGE_SPRITE = "sprite";
    public static final String PERSONNAGE_CLE_JOUEUR = "idJoueur";
    public static final String PERSONNAGE_CLE_CLASSE = "idClasse";
    public static final String PERSONNAGE_CLE_MAP = "idMap";
    public static final String PERSONNAGE_CLE_STUFF_TETE = "idStuffTete";
    public static final String PERSONNAGE_CLE_STUFF_TORSE = "idStuffTorse";
    public static final String PERSONNAGE_CLE_STUFF_GANT= "idStuffGant";
    public static final String PERSONNAGE_CLE_STUFF_JAMBE = "idStuffJambe";
    public static final String PERSONNAGE_CLE_STUFF_BOTTE = "idStuffBotte";
    public static final String PERSONNAGE_CLE_STUFF_ARME = "idStuffArme";
    public static final String PERSONNAGE_CLE_FACTION = "idFaction";
    public static final String PERSONNAGE_CLE_GUILDE = "idGuilde";
    public static final String PERSONNAGE_CLE_TITRE = "idTitre";

    private static final String CREATION_TABLE_PERSONNAGE =
            "CREATE TABLE " + NOM_TABLE_PERSONNAGE + " ( "
                + PERSONNAGE_CLE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PERSONNAGE_NOM + " TEXT NOT NULL,"
                + PERSONNAGE_SPRITE + " TEXT NOT NULL,"
                + PERSONNAGE_CLE_JOUEUR + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_JOUEUR + " (" + JOUEUR_CLE + "),"
                + PERSONNAGE_CLE_CLASSE + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_CLASSE + " (" + CLASSE_CLE + "),"
                + PERSONNAGE_CLE_MAP + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_MAP + " (" + MAP_CLE + "),"
                + PERSONNAGE_CLE_STUFF_TETE  + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_OBJET + " (" + OBJET_CLE + "),"
                + PERSONNAGE_CLE_STUFF_TORSE + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_OBJET + " (" + OBJET_CLE + "),"
                + PERSONNAGE_CLE_STUFF_GANT  + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_OBJET + " (" + OBJET_CLE + "),"
                + PERSONNAGE_CLE_STUFF_JAMBE + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_OBJET + " (" + OBJET_CLE + "),"
                + PERSONNAGE_CLE_STUFF_BOTTE + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_OBJET + " (" + OBJET_CLE + "),"
                + PERSONNAGE_CLE_STUFF_ARME  + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_OBJET + " (" + OBJET_CLE + "),"
                + PERSONNAGE_CLE_FACTION + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_FACTION + " (" + FACTION_CLE + "),"
                + PERSONNAGE_CLE_GUILDE  + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_GUILDE + " (" + GUILDE_CLE + "),"
                + PERSONNAGE_CLE_TITRE   + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_TITRE + " (" + TITRE_CLE + ")"
            + ") ;";


    ///////// ETAPES QUETES //////////////////
    // TODO finir d'ecrire les table suivante : EtapeQuete

    ///////// QUETES //////////////////
    // TODO finir d'ecrire les table suivante : Quete

    ///////// LISTE QUETES //////////////////
    // TODO finir d'ecrire les table suivante : ListeQuete


    ///////// TABLE Personnage Non Joueur ///////////////////
    public static final String NOM_TABLE_PNJ = "PersonnageNonJoueur";
    public static final String PNJ_CLE = "_id";
    public static final String PNJ_NOM = "sprite";
    public static final String PNJ_STAT_ARMURE = "statArmure";
    public static final String PNJ_STAT_FORCE = "statForce";
    public static final String PNJ_STAT_INTELLIGENCE = "statIntelligence";
    public static final String PNJ_STAT_AGILITE = "statAgilite";
    public static final String PNJ_STAT_ENDURANCE = "statEndurence";
    public static final String PNJ_STAT_SAGESSE = "statSagesse";
    public static final String PNJ_CLE_MAP = "idMap";
    public static final String PNJ_CLE_FACTION = "idFaction";
    public static final String PNJ_CLE_TITRE = "idTitre";
    public static final String PNJ_QUETE = "idQuete";

    private static final String CREATION_TABLE_PNJ =
            "CREATE TABLE " + NOM_TABLE_PNJ + " ("
                + PNJ_CLE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PNJ_NOM + " TEXT NOT NULL,"
                + PNJ_STAT_ARMURE       + " INTEGER NOT NULL,"
                + PNJ_STAT_FORCE        + " INTEGER NOT NULL,"
                + PNJ_STAT_INTELLIGENCE + " INTEGER NOT NULL,"
                + PNJ_STAT_AGILITE      + " INTEGER NOT NULL,"
                + PNJ_STAT_ENDURANCE    + " INTEGER NOT NULL,"
                + PNJ_STAT_SAGESSE      + " INTEGER NOT NULL,"
                + PNJ_CLE_MAP + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_MAP + " (" + MAP_CLE + "),"
                + PNJ_CLE_FACTION + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_FACTION + " (" + FACTION_CLE + "),"
                + PNJ_CLE_TITRE + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_TITRE + " (" + TITRE_CLE + ")"
                    // TODO ajouter la FK pour les quetes
            + ");";


    ////// TABLE LISTE EFFET ///////
    public static final String NOM_TABLE_LISTE_EFFET = "ListeEffet";
    public static final String LISTE_EFFET_CLE_EFFET = "idEffet";
    public static final String LISTE_EFFET_CLE_PERSO = "idPersonnage";

    private static final String CREATION_TABLE_LISTE_EFFET =
            "CREATE TABLE " + NOM_TABLE_LISTE_EFFET + " ("
                + LISTE_EFFET_CLE_EFFET + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_EFFET + " (" + EFFET_CLE + "),"
                + LISTE_EFFET_CLE_PERSO + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_PERSONNAGE + " (" + PERSONNAGE_CLE + "),"
                + "PRIMARY KEY (" + LISTE_EFFET_CLE_EFFET + ", " + LISTE_EFFET_CLE_PERSO + ")"
            + ");";

    Connection connection;
    Statement statement;

    public HelperBD(String nomBD) {
        open(nomBD);
        executeUpdate(CREATION_TABLE_JOUEUR);
        executeUpdate(CREATION_TABLE_CLASSE);
        executeUpdate(CREATION_TABLE_EFFET);
        executeUpdate(CREATION_TABLE_COMPETENCE);
        executeUpdate(CREATION_TABLE_MAP);
        executeUpdate(CREATION_TABLE_OBJET);
        executeUpdate(CREATION_TABLE_FACTION);
        executeUpdate(CREATION_TABLE_GUILDE);
        executeUpdate(CREATION_TABLE_TITRE);
        executeUpdate(CREATION_TABLE_PERSONNAGE);
        executeUpdate(CREATION_TABLE_PNJ);
        executeUpdate(CREATION_TABLE_LISTE_EFFET);
        //initDataJoueur();
    }

    private void open(String nomBD) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + nomBD);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    /**
     * Execute une updateSQL sur la BD
     * @param sql la requetes a executer
     */
    public void executeUpdate(String sql) {
        statement = null;

        try {
            statement =  connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Execute une requetes SQL sur la BD
     * @param sql la requetes a executer
     */
    public ResultSet executeRequete(String sql) {
        statement = null;
        ResultSet rs = null;

        try {
            statement =  connection.createStatement();
            rs = statement.executeQuery(sql);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return rs;
    }

    public void close() throws SQLException {
        statement.close();
        connection.close();
    }

    private void initDataJoueur() {
        executeUpdate(
                "INSERT INTO JOUEUR (" + JOUEUR_PSEUDO + ","+ JOUEUR_MDP + ")"
                + "VALUES           (    \"Jean Test\",           \"leserveur\"     );"
        );
    }


}
