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

    ///// TABLE PERSONNAGE ///////
    public static final String NOM_TABLE_PERSONNAGE = "Personnage";
    public static final String PERSONNAGE_CLE = "_id";
    public static final String PERSONNAGE_NOM = "nom";
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
                + PERSONNAGE_CLE_JOUEUR + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_JOUEUR + " (" + JOUEUR_CLE + "),"
                + PERSONNAGE_CLE_CLASSE + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_CLASSE + " (" + CLASSE_CLE + ")"
            + ") ;";

    Connection connection;
    Statement statement;

    public HelperBD(String nomBD) {
        open(nomBD);
        executeUpdate(CREATION_TABLE_JOUEUR);
        executeUpdate(CREATION_TABLE_CLASSE);
        executeUpdate(CREATION_TABLE_EFFET);
        executeUpdate(CREATION_TABLE_COMPETENCE);
        executeUpdate(CREATION_TABLE_PERSONNAGE);
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
