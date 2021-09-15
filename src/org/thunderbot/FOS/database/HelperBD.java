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
    public static final String CLASSE_STAT_ENDURANCE = "statEndurance";
    public static final String CLASSE_STAT_SAGESSE = "statSagesse";

    private static final String CREATION_TABLE_CLASSE =
            "CREATE TABLE " + NOM_TABLE_CLASSE + " ( "
            + CLASSE_CLE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CLASSE_NOM + " TEXT NOT NULL,"
            + CLASSE_STAT_AGILITE + " INTEGER NOT NULL,"
            + CLASSE_STAT_ARMURE + " INTEGER NOT NULL,"
            + CLASSE_STAT_ENDURANCE + " INTEGER NOT NULL,"
            + CLASSE_STAT_FORCE + " INTEGER NOT NULL,"
            + CLASSE_STAT_INTELLIGENCE + " INTEGER NOT NULL,"
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
    public static final String EFFET_STAT_ENDURANCE = "statEndurance";
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
    public static final String OBJET_STAT_ENDURANCE = "statEndurance";
    public static final String OBJET_STAT_SAGESSE = "statSagesse";
    public static final String OBJET_DPS = "dps";
    public static final String OBJET_IMAGE = "image";

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
                + OBJET_IMAGE + " TEXT NOT NULL"
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
    public static final String PERSONNAGE_X = "x";
    public static final String PERSONNAGE_Y = "y";
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
                + PERSONNAGE_X + " FLOAT NOT NULL,"
                + PERSONNAGE_Y + " FLOAT NOT NULL,"
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
    public static final String PNJ_NOM = "nom";
    public static final String PNJ_SPRITE = "sprite";
    public static final String PNJ_AGRESSIF = "agressif";
    public static final String PNJ_STAT_ARMURE = "statArmure";
    public static final String PNJ_STAT_FORCE = "statForce";
    public static final String PNJ_STAT_INTELLIGENCE = "statIntelligence";
    public static final String PNJ_STAT_AGILITE = "statAgilite";
    public static final String PNJ_STAT_ENDURANCE = "statEndurance";
    public static final String PNJ_STAT_SAGESSE = "statSagesse";
    public static final String PNJ_CLE_MAP = "idMap";
    public static final String PNJ_CLE_FACTION = "idFaction";
    public static final String PNJ_CLE_TITRE = "idTitre";
    public static final String PNJ_QUETE = "idQuete";

    private static final String CREATION_TABLE_PNJ =
            "CREATE TABLE " + NOM_TABLE_PNJ + " ("
                + PNJ_CLE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PNJ_NOM + " TEXT NOT NULL,"
                + PNJ_SPRITE + " TEXT NOT NULL,"
                + PNJ_AGRESSIF + " INTEGER NOT NULL,"
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
    public static final String LISTE_EFFET_DUREE_RESTANTE = "dureeRestante";

    private static final String CREATION_TABLE_LISTE_EFFET =
            "CREATE TABLE " + NOM_TABLE_LISTE_EFFET + " ("
                + LISTE_EFFET_CLE_EFFET + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_EFFET + " (" + EFFET_CLE + "),"
                + LISTE_EFFET_CLE_PERSO + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_PERSONNAGE + " (" + PERSONNAGE_CLE + "),"
                + LISTE_EFFET_DUREE_RESTANTE +  " INTEGER NOT NULL,"
                + "PRIMARY KEY (" + LISTE_EFFET_CLE_EFFET + ", " + LISTE_EFFET_CLE_PERSO + ")"
            + ");";


    ////// TABLE LISTE COMPETENCE ///////
    public static final String NOM_TABLE_LISTE_COMPETENCE = "ListeCompetence";
    public static final String LISTE_COMPETENCE_CLE_COMPETENCE = "idCompetence";
    public static final String LISTE_COMPETENCE_CLE_PERSO = "idPersonnage";
    public static final String LISTE_COMPETENCE_EXPERIENCE = "experience";

    private static final String CREATION_TABLE_LISTE_COMPETENCE =
            "CREATE TABLE " + NOM_TABLE_LISTE_COMPETENCE + " ("
                    + LISTE_COMPETENCE_CLE_COMPETENCE + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_COMPETENCE + " (" + COMPETENCE_CLE + "),"
                    + LISTE_COMPETENCE_CLE_PERSO + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_PERSONNAGE + " (" + PERSONNAGE_CLE + "),"
                    + LISTE_COMPETENCE_EXPERIENCE +  " INTEGER NOT NULL,"
                    + "PRIMARY KEY (" + LISTE_COMPETENCE_CLE_COMPETENCE + ", " + LISTE_COMPETENCE_CLE_PERSO + ")"
                    + ");";


    ////// TABLE LISTE COMPETENCE PNJ ///////
    public static final String NOM_TABLE_LISTE_COMPETENCE_PNJ = "ListeCompetencePNJ";
    public static final String LISTE_COMPETENCE_PNJ_CLE_COMPETENCE = "idCompetence";
    public static final String LISTE_COMPETENCE_PNJ_CLE_PERSO = "idPersonnageNonJoueur";
    public static final String LISTE_COMPETENCE_PNJ_EXPERIENCE = "experience";

    private static final String CREATION_TABLE_LISTE_COMPETENCE_PNJ =
            "CREATE TABLE " + NOM_TABLE_LISTE_COMPETENCE_PNJ + " ("
                    + LISTE_COMPETENCE_PNJ_CLE_COMPETENCE + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_COMPETENCE + " (" + COMPETENCE_CLE + "),"
                    + LISTE_COMPETENCE_PNJ_CLE_PERSO + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_PERSONNAGE + " (" + PERSONNAGE_CLE + "),"
                    + LISTE_COMPETENCE_PNJ_EXPERIENCE +  " INTEGER NOT NULL,"
                    + "PRIMARY KEY (" + LISTE_COMPETENCE_PNJ_CLE_COMPETENCE + ", " + LISTE_COMPETENCE_PNJ_CLE_PERSO + ")"
                    + ");";


    ////// TABLE INVENTAIRE ///////
    public static final String NOM_TABLE_INVENTAIRE = "Inventaire";
    public static final String INVENTAIRE_CLE_PERSONNAGE = "idPersonnage";
    public static final String INVENTAIRE_CLE_OBJET = "idObjet";

    private static final String CREATION_TABLE_INVENTAIRE =
            "CREATE TABLE " + NOM_TABLE_INVENTAIRE + " ("
                    + INVENTAIRE_CLE_PERSONNAGE + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_PERSONNAGE + " (" + PERSONNAGE_CLE + "),"
                    + INVENTAIRE_CLE_OBJET + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_OBJET + " (" + OBJET_CLE + "),"
                    + "PRIMARY KEY (" + INVENTAIRE_CLE_PERSONNAGE + ", " + INVENTAIRE_CLE_OBJET + ")"
                    + ");";

    ////// TABLE LISTE OBJET LOOTABLE ///////
    public static final String NOM_TABLE_LISTE_OBJET_LOOTABLE = "ListeObjetLootable";
    public static final String LISTE_OBJET_LOOTABLE_CLE_PNJ = "idPersonnageNonJoueur";
    public static final String LISTE_OBJET_LOOTABLE_CLE_OBJET = "idObjet";

    private static final String CREATION_TABLE_LISTE_OBJET_LOOTABLE =
            "CREATE TABLE " + NOM_TABLE_LISTE_OBJET_LOOTABLE + " ("
                    + LISTE_OBJET_LOOTABLE_CLE_PNJ + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_PNJ + " (" + PNJ_CLE + "),"
                    + LISTE_OBJET_LOOTABLE_CLE_OBJET + " INTEGER NOT NULL REFERENCES " + NOM_TABLE_OBJET + " (" + OBJET_CLE + "),"
                    + "PRIMARY KEY (" + LISTE_OBJET_LOOTABLE_CLE_PNJ + ", " + LISTE_OBJET_LOOTABLE_CLE_OBJET + ")"
                    + ");";

    Connection connection;
    Statement statement;

    public HelperBD(String nomBD) {
        open(nomBD);
        //initDataJoueur();
    }

    private void open(String nomBD) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + nomBD);
            initTable();
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

    private void initTable() throws SQLException {

        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet tables = dbm.getTables(null, null, NOM_TABLE_JOUEUR, null);

        if (!tables.next()) {
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
            executeUpdate(CREATION_TABLE_LISTE_COMPETENCE);
            executeUpdate(CREATION_TABLE_LISTE_COMPETENCE_PNJ);
            executeUpdate(CREATION_TABLE_INVENTAIRE);
            executeUpdate(CREATION_TABLE_LISTE_OBJET_LOOTABLE);

            System.out.println("Création des tables terminée !");

            // INIT DATA TODO enlever
            System.out.println("Création du joueur test");
            initDataJoueur();
            initDataClasse();
            initPersonnage();
            initMap();
            initObjet();
        }
    }

    private void initDataJoueur() {
        executeUpdate(
                "INSERT INTO JOUEUR (" + JOUEUR_PSEUDO + ","+ JOUEUR_MDP + ")"
                        + "VALUES           (    'JeanTest',           'leserveur'     );"
        );
        System.out.println("Initialisation du joueur");
    }

    private void initDataClasse() {
        executeUpdate(
                "INSERT INTO " + NOM_TABLE_CLASSE
                        + " ( " + CLASSE_NOM + ", " + CLASSE_STAT_AGILITE + ", " + CLASSE_STAT_ARMURE
                        + ", " + CLASSE_STAT_ENDURANCE + ", " + CLASSE_STAT_FORCE + ", " + CLASSE_STAT_INTELLIGENCE
                        + ", " + CLASSE_STAT_SAGESSE +  ")"
                    + "VALUES ( 'TestClassse', 1, 1, 1, 1, 1, 1); ");

        System.out.println("Initialisation de la classe");
    }

    private void initPersonnage() {
        executeUpdate(
                "INSERT INTO " + NOM_TABLE_PERSONNAGE
                        + " ( " + PERSONNAGE_NOM + ", " + PERSONNAGE_SPRITE + ", " + PERSONNAGE_X
                        + ", " + PERSONNAGE_Y + ", " + PERSONNAGE_CLE_JOUEUR
                        + ", " + PERSONNAGE_CLE_CLASSE + ", " + PERSONNAGE_CLE_MAP + ", " + PERSONNAGE_CLE_STUFF_TETE
                        + ", " + PERSONNAGE_CLE_STUFF_TORSE + ", " + PERSONNAGE_CLE_STUFF_GANT + ", " + PERSONNAGE_CLE_STUFF_JAMBE
                        + ", " + PERSONNAGE_CLE_STUFF_BOTTE + ", " + PERSONNAGE_CLE_STUFF_ARME + ", " + PERSONNAGE_CLE_FACTION
                        + ", " + PERSONNAGE_CLE_GUILDE + ", " + PERSONNAGE_CLE_TITRE +  ")"
                        + "VALUES ( 'TestPerso', 'image', 650 ,400 ,1, 1, 1, 2, 3, 4, 5, 6, 7, 1, 1, 1); ");

        System.out.println("Initialisation du personnage");
    }

    private void initMap() {
        executeUpdate(
                "INSERT INTO " + NOM_TABLE_MAP
                        + " ( " + MAP_NOM + ", " + MAP_NIVEAU_PNJ + ", " + MAP_NOMBRE_MOB +  ")"
                        + "VALUES ( 'map_campagne_ThunderSun.tmx', 1, 3); "
        );

        executeUpdate(
                "INSERT INTO " + NOM_TABLE_MAP
                        + " ( " + MAP_NOM + ", " + MAP_NIVEAU_PNJ + ", " + MAP_NOMBRE_MOB +  ")"
                        + "VALUES ( 'map_grotte.tmx', 1, 3); "
        );

        System.out.println("Initialisation de la carte");
    }

    private void initObjet() {

        // Objet vide de l'inventaire
        executeUpdate("" +
                "INSERT INTO " + NOM_TABLE_OBJET + " ( "
                + OBJET_NOM + ", " + OBJET_EMPLACEMENT + ", " + OBJET_STAT_AGILITE + ", " +  OBJET_STAT_ARMURE + ", "
                + OBJET_STAT_ENDURANCE + ", " + OBJET_STAT_FORCE + ", " + OBJET_STAT_INTELLIGENCE + ", " + OBJET_STAT_SAGESSE + ", "
                + OBJET_DPS + ", " + OBJET_IMAGE + " ) "
                + "VALUES ( 'Vide', 0, 0, 0, 0, 0, 0, 0, 0, 'vide.png' ); "

        );

        // Casque
        executeUpdate("" +
                "INSERT INTO " + NOM_TABLE_OBJET + " ( "
                + OBJET_NOM + ", " + OBJET_EMPLACEMENT + ", " + OBJET_STAT_AGILITE + ", " +  OBJET_STAT_ARMURE + ", "
                + OBJET_STAT_ENDURANCE + ", " + OBJET_STAT_FORCE + ", " + OBJET_STAT_INTELLIGENCE + ", " + OBJET_STAT_SAGESSE + ", "
                + OBJET_DPS + ", " + OBJET_IMAGE + " ) "
                + "VALUES ( 'Casque', 1, 1, 1, 1, 1, 1, 1, 0, 'casque.png' ); "

        );

        // Plastron
        executeUpdate("" +
                "INSERT INTO " + NOM_TABLE_OBJET + " ( "
                + OBJET_NOM + ", " + OBJET_EMPLACEMENT + ", " + OBJET_STAT_AGILITE + ", " +  OBJET_STAT_ARMURE + ", "
                + OBJET_STAT_ENDURANCE + ", " + OBJET_STAT_FORCE + ", " + OBJET_STAT_INTELLIGENCE + ", " + OBJET_STAT_SAGESSE + ", "
                + OBJET_DPS + ", " + OBJET_IMAGE + " ) "
                + "VALUES ( 'Plastron', 2, 1, 1, 1, 1, 1, 1, 0, 'plastron.png' ); "

        );

        // Gant
        executeUpdate("" +
                "INSERT INTO " + NOM_TABLE_OBJET + " ( "
                + OBJET_NOM + ", " + OBJET_EMPLACEMENT + ", " + OBJET_STAT_AGILITE + ", " +  OBJET_STAT_ARMURE + ", "
                + OBJET_STAT_ENDURANCE + ", " + OBJET_STAT_FORCE + ", " + OBJET_STAT_INTELLIGENCE + ", " + OBJET_STAT_SAGESSE + ", "
                + OBJET_DPS + ", " + OBJET_IMAGE + " ) "
                + "VALUES ( 'Gant', 3, 1, 1, 1, 1, 1, 1, 0, 'gant.png' ); "

        );

        // Jambiere
        executeUpdate("" +
                "INSERT INTO " + NOM_TABLE_OBJET + " ( "
                + OBJET_NOM + ", " + OBJET_EMPLACEMENT + ", " + OBJET_STAT_AGILITE + ", " +  OBJET_STAT_ARMURE + ", "
                + OBJET_STAT_ENDURANCE + ", " + OBJET_STAT_FORCE + ", " + OBJET_STAT_INTELLIGENCE + ", " + OBJET_STAT_SAGESSE + ", "
                + OBJET_DPS + ", " + OBJET_IMAGE + " ) "
                + "VALUES ( 'Jambiere', 4, 1, 1, 1, 1, 1, 1, 0, 'jambiere.png' ); "
        );

        // Botte
        executeUpdate("" +
                "INSERT INTO " + NOM_TABLE_OBJET + " ( "
                + OBJET_NOM + ", " + OBJET_EMPLACEMENT + ", " + OBJET_STAT_AGILITE + ", " +  OBJET_STAT_ARMURE + ", "
                + OBJET_STAT_ENDURANCE + ", " + OBJET_STAT_FORCE + ", " + OBJET_STAT_INTELLIGENCE + ", " + OBJET_STAT_SAGESSE + ", "
                + OBJET_DPS + ", " + OBJET_IMAGE + " ) "
                + "VALUES ( 'Botte', 5, 1, 1, 1, 1, 1, 1, 0, 'botte.png' ); "
        );

        // Hache
        executeUpdate("" +
                "INSERT INTO " + NOM_TABLE_OBJET + " ( "
                + OBJET_NOM + ", " + OBJET_EMPLACEMENT + ", " + OBJET_STAT_AGILITE + ", " +  OBJET_STAT_ARMURE + ", "
                + OBJET_STAT_ENDURANCE + ", " + OBJET_STAT_FORCE + ", " + OBJET_STAT_INTELLIGENCE + ", " + OBJET_STAT_SAGESSE + ", "
                + OBJET_DPS + ", " + OBJET_IMAGE + " ) "
                + "VALUES ( 'Hache', 6, 1, 1, 1, 1, 1, 1, 2, 'hache.png' ); "
        );

        System.out.println("Initialisation des objets");
    }


}
