package org.thunderbot.FOS.database;

import static org.thunderbot.FOS.database.HelperBD.*;

/**
 * Initialisation des donnée de base du jeu, pour pouvoir joueur
 */
public class InitDataJeu {

    private static final String CREATION_CLASSE =
            "INSERT INTO " + NOM_TABLE_CLASSE
                + " ( " + CLASSE_NOM + ", " + CLASSE_STAT_AGILITE + ", " + CLASSE_STAT_ARMURE + ", " + CLASSE_STAT_DEXTERITE
                + ", " + CLASSE_STAT_ENDURANCE + ", " + CLASSE_STAT_FORCE + ", " + CLASSE_STAT_INTELLIGENCE
                + ", " + CLASSE_STAT_SAGESSE +  ")"
            + "VALUES "
                + "( 'Archer'  , 5, 3, 4, 2, 3, 2, 1),"
                + "( 'Mage'    , 2, 1, 2, 3, 1, 5, 4),"
                + "( 'Pretre'  , 3, 2, 3, 2, 1, 4, 5),"
                + "( 'Guerrier', 2, 4, 2, 4, 4, 1, 1),"
                + "( 'Voleur'  , 4, 3, 4, 2, 4, 2, 1),"
                + "( 'Pugilat' , 4, 2, 3, 2, 5, 1, 1);"
    ;

    private static final String CREATION_COMPETENCE =
            "INSERT INTO " + NOM_TABLE_COMPETENCE
                + " ( " + COMPETENCE_NOM + ", " + COMPETENCE_DEGAT_BASE + ", " + COMPETENCE_PORTEE + ", " + COMPETENCE_COUT + ", " + COMPETENCE_ID_EFFET + ", " + COMPETENCE_IMAGE + " )"
            + "VALUES "
                + " ( 'Coup de point'            , 1, 1, 1, 1, 'coupDePoint.png'),"
                + " ( 'Tir de fleche'            , 1, 1, 5, 1, 'fleche.png'),"
                + " ( 'Tir de fleche piégée'     , 1, 1, 3, 2, 'flechePiege.png'),"
                + " ( 'Tir de fleche explosive'  , 1, 1, 3, 2, 'flecheExplosive.png'),"
                + " ( 'Tir de fleche empoisonnée', 0, 2, 3, 2, 'flecheEmpoisonnee.png');"
            ;

    private static final String CREATION_EFFET =
            "INSERT INTO " + NOM_TABLE_EFFET
                + " ( " + EFFET_NOM + ", "
                        + EFFET_STAT_AGILITE + ", "
                        + EFFET_STAT_ARMURE + ", "
                        + EFFET_STAT_DEXTERITE + ", "
                        + EFFET_STAT_ENDURANCE + ", "
                        + EFFET_STAT_FORCE + ", "
                        + EFFET_STAT_INTELLIGENCE + ", "
                        + EFFET_STAT_SAGESSE + ", "
                        + EFFET_DPS + ", "
                        + EFFET_DUREE + ", "
                        + EFFET_IMAGE
                + " ) "
            + " VALUES "
                + " ( 'DEFAUT', 0, 0, 0, 0, 0, 0, 0, 0, 0, 'effet.png' ),"
                + " ( 'POISON', 0, 0, 0, 0, 0, 0, 0, 1, 3, 'effet.png' );";


    private static final String CREATION_FACTION =
            "INSERT INTO " + NOM_TABLE_FACTION
                + " ( " + FACTION_NOM + "," + FACTION_CLE_MAP_START + " ) "
            + "VALUES "
                + " ('Idenia',   1),"
                + " ('Umbra',    2),"
                + " ('Ethernia', 3);"
    ;

    private static final String CREATION_MAP =
            "INSERT INTO " + NOM_TABLE_MAP
                    + " ( " + MAP_NOM + ", " + MAP_NIVEAU_PNJ + ", " + MAP_NOMBRE_MOB +  ")"
            + "VALUES "
                + "( 'map_start_idenia.tmx', 1, 3),"
                + "( 'map_start_umbra.tmx', 1, 3),"
                + "( 'map_start_ethernia.tmx', 1, 3),"
                + "( 'map_desert.tmx', 2, 3),"
                + "( 'map_grotte.tmx', 3, 3),"
                + "( 'map_cimetiere.tmx', 3, 3);"
    ;

    private static String CREATION_OBJET =
            "INSERT INTO " + NOM_TABLE_OBJET + " ( "
                    + OBJET_NOM + ", " + OBJET_DESC + ", " + OBJET_EMPLACEMENT + ", "    + OBJET_ID_CLASSE + ", "
                    + OBJET_STAT_AGILITE + ", " +  OBJET_STAT_ARMURE + ", " + OBJET_STAT_DEXTERITE + ", "
                    + OBJET_STAT_ENDURANCE + ", " + OBJET_STAT_FORCE + ", " + OBJET_STAT_INTELLIGENCE + ", " + OBJET_STAT_SAGESSE + ", "
                    + OBJET_DPS + ", " + OBJET_IMAGE + " ) "
            + "VALUES "
                    + "( 'Livre d''histoire', 'Un livre racontant l''Histoire du Bellum Deorum', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'livre.png'), "
                    + "( 'Chapeau'          , 'Un vieux chapeau usé par le temps'               ,1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 'chapeau.png'), "
                    + "( 'Chemise'          , 'Une vieille chemise usé par le temps'            ,2, 0, 1, 1, 1, 1, 1, 1, 1, 0, 'chemise.png'), "
                    + "( 'Mitaine'          , 'Deux vieilles mitaines usés par le temps'        ,3, 0, 1, 1, 1, 1, 1, 1, 1, 0, 'mitaine.png'), "
                    + "( 'Pantalon'         , 'Un vieux pantalon usé par le temps'              ,4, 0, 1, 1, 1, 1, 1, 1, 1, 0, 'pantalon.png'), "
                    + "( 'Tong'             , 'Deux vieilles tongs usés par le temps'           ,5, 0, 1, 1, 1, 1, 1, 1, 1, 0, 'tong.png'), "
                    + "( 'Baton De Base'    , 'Un vieux baton usé par le temps'                 ,6, 0, 1, 1, 1, 1, 1, 1, 1, 1, 'baton.png');";

    private static String CREATION_PNJ =
            "INSERT INTO " + NOM_TABLE_PNJ + " ( "
                + PNJ_NOM + ", "
                + PNJ_SPRITE + ", "
                + PNJ_AGRESSIF + ", "
                + PNJ_X + ", "
                + PNJ_Y + ", "
                + PNJ_STAT_AGILITE + ", "
                + PNJ_STAT_ARMURE + ", "
                + PNJ_STAT_DEXTERITE + ", "
                + PNJ_STAT_ENDURANCE + ", "
                + PNJ_STAT_FORCE + ", "
                + PNJ_STAT_INTELLIGENCE + ", "
                + PNJ_STAT_SAGESSE + ", "
                + PNJ_CLE_MAP + ", "
                + PNJ_CLE_FACTION + ", "
                + PNJ_CLE_TITRE + " ) "
            + "VALUES "
                + "( 'JORG', 'necromant.png', 1, 680, 450, 5, 5, 5, 5, 5, 5, 5, 1, 1, 0),"
                + "( 'MORG', 'necromant.png', 1, 680, 450, 5, 5, 5, 5, 5, 5, 5, 2, 2, 0),"
                + "( 'WORG', 'necromant.png', 1, 680, 450, 5, 5, 5, 5, 5, 5, 5, 3, 3, 0)"
            + ";";

    private static final String CREATION_PERSONNAGE =
            "INSERT INTO " + NOM_TABLE_PERSONNAGE + " ( "
                    + PERSONNAGE_NOM + ", "
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
            + "VALUES "
            + "( 'TestPerso', 'sprite2.png', 640, 400, 1, 1, 1, 2, 3, 4, 5, 6, 7, 1, 1, 1)"
            + ";";

    private static final String CREATION_JOUEUR =
            "INSERT INTO " + NOM_TABLE_JOUEUR + " ( "
                    + JOUEUR_PSEUDO + ","
                    + JOUEUR_MDP + ")"
            + "VALUES "
            + "( 'JeanTest', 'leserveur')"
            + ";";

    private static final String CREATION_LISTE_COMPETENCE =
            "INSERT INTO " + NOM_TABLE_LISTE_COMPETENCE + " ("
                    + LISTE_COMPETENCE_CLE_COMPETENCE + ", "
                    + LISTE_COMPETENCE_CLE_PERSO + ", "
                    + LISTE_COMPETENCE_EXPERIENCE +  ")"
            + "VALUES "
            + "( '1', '1', 0),"
            + "( '2', '1', 0),"
            + "( '3', '1', 0),"
            + "( '4', '1', 0),"
            + "( '5', '1', 0)"
           + ";";

    public static void main(String[] args) {
        HelperBD helperBD = new HelperBD(FosDAO.NOM_BD);
        helperBD.executeUpdate(CREATION_CLASSE);
        helperBD.executeUpdate(CREATION_COMPETENCE);
        helperBD.executeUpdate(CREATION_EFFET);
        helperBD.executeUpdate(CREATION_FACTION);
        helperBD.executeUpdate(CREATION_MAP);
        helperBD.executeUpdate(CREATION_PNJ);
        helperBD.executeUpdate(CREATION_OBJET);
        helperBD.executeUpdate(CREATION_JOUEUR);
        helperBD.executeUpdate(CREATION_PERSONNAGE);
        helperBD.executeUpdate(CREATION_LISTE_COMPETENCE);
    }
}
