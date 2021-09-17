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
                + "( 'Pugila'  , 4, 2, 3, 2, 5, 1, 1);"
    ;

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
                + "( 'map_grotte.tmx', 3, 3);"
    ;

    private static String CREATION_OBJET =
            "INSERT INTO " + NOM_TABLE_OBJET + " ( "
                    + OBJET_NOM + ", " + OBJET_EMPLACEMENT + ", " + OBJET_STAT_AGILITE + ", " +  OBJET_STAT_ARMURE + ", "
                    + OBJET_STAT_DEXTERITE + ", "
                    + OBJET_STAT_ENDURANCE + ", " + OBJET_STAT_FORCE + ", " + OBJET_STAT_INTELLIGENCE + ", " + OBJET_STAT_SAGESSE + ", "
                    + OBJET_DPS + ", " + OBJET_IMAGE + " ) "
            + "VALUES "
                    + "( 'Livre d''histoire', 0, 0, 0, 0, 0, 0, 0, 0, 0, 'casque.png'), "
                    + "( 'Casque De Base', 1, 1, 1, 1, 1, 1, 1, 1, 0, 'casque.png'), "
                    + "( 'Torse De Base', 1, 1, 1, 1, 1, 1, 1, 1, 0, 'torse.png'), "
                    + "( 'Gant De Base', 1, 1, 1, 1, 1, 1, 1, 1, 0, 'torse.png'), "
                    + "( 'Pantalon De Base', 1, 1, 1, 1, 1, 1, 1, 1, 0, 'torse.png'), "
                    + "( 'Botte De Base', 1, 1, 1, 1, 1, 1, 1, 1, 0, 'torse.png'), "
                    + "( 'Baton De Base', 1, 1, 1, 1, 1, 1, 1, 1, 1, 'torse.png');";

    public static void main(String[] args) {
        HelperBD helperBD = new HelperBD(FosDAO.NOM_BD);
        helperBD.executeUpdate(CREATION_CLASSE);
        helperBD.executeUpdate(CREATION_FACTION);
        helperBD.executeUpdate(CREATION_MAP);
        helperBD.executeUpdate(CREATION_OBJET);
    }


//    private void initObjet() {
//
//        // Objet vide de l'inventaire
//        executeUpdate("" +
//                "INSERT INTO " + NOM_TABLE_OBJET + " ( "
//                + OBJET_NOM + ", " + OBJET_EMPLACEMENT + ", " + OBJET_STAT_AGILITE + ", " +  OBJET_STAT_ARMURE + ", "
//                + OBJET_STAT_ENDURANCE + ", " + OBJET_STAT_FORCE + ", " + OBJET_STAT_INTELLIGENCE + ", " + OBJET_STAT_SAGESSE + ", "
//                + OBJET_DPS + ", " + OBJET_IMAGE + " ) "
//                + "VALUES ( 'Vide', 0, 0, 0, 0, 0, 0, 0, 0, 'vide.png' ); "
//
//        );
//
//        // Casque
//        executeUpdate("" +
//                "INSERT INTO " + NOM_TABLE_OBJET + " ( "
//                + OBJET_NOM + ", " + OBJET_EMPLACEMENT + ", " + OBJET_STAT_AGILITE + ", " +  OBJET_STAT_ARMURE + ", "
//                + OBJET_STAT_ENDURANCE + ", " + OBJET_STAT_FORCE + ", " + OBJET_STAT_INTELLIGENCE + ", " + OBJET_STAT_SAGESSE + ", "
//                + OBJET_DPS + ", " + OBJET_IMAGE + " ) "
//                + "VALUES ( 'Casque', 1, 1, 1, 1, 1, 1, 1, 0, 'casque.png' ); "
//
//        );
//
//        // Plastron
//        executeUpdate("" +
//                "INSERT INTO " + NOM_TABLE_OBJET + " ( "
//                + OBJET_NOM + ", " + OBJET_EMPLACEMENT + ", " + OBJET_STAT_AGILITE + ", " +  OBJET_STAT_ARMURE + ", "
//                + OBJET_STAT_ENDURANCE + ", " + OBJET_STAT_FORCE + ", " + OBJET_STAT_INTELLIGENCE + ", " + OBJET_STAT_SAGESSE + ", "
//                + OBJET_DPS + ", " + OBJET_IMAGE + " ) "
//                + "VALUES ( 'Plastron', 2, 1, 1, 1, 1, 1, 1, 0, 'plastron.png' ); "
//
//        );
//
//        // Gant
//        executeUpdate("" +
//                "INSERT INTO " + NOM_TABLE_OBJET + " ( "
//                + OBJET_NOM + ", " + OBJET_EMPLACEMENT + ", " + OBJET_STAT_AGILITE + ", " +  OBJET_STAT_ARMURE + ", "
//                + OBJET_STAT_ENDURANCE + ", " + OBJET_STAT_FORCE + ", " + OBJET_STAT_INTELLIGENCE + ", " + OBJET_STAT_SAGESSE + ", "
//                + OBJET_DPS + ", " + OBJET_IMAGE + " ) "
//                + "VALUES ( 'Gant', 3, 1, 1, 1, 1, 1, 1, 0, 'gant.png' ); "
//
//        );
//
//        // Jambiere
//        executeUpdate("" +
//                "INSERT INTO " + NOM_TABLE_OBJET + " ( "
//                + OBJET_NOM + ", " + OBJET_EMPLACEMENT + ", " + OBJET_STAT_AGILITE + ", " +  OBJET_STAT_ARMURE + ", "
//                + OBJET_STAT_ENDURANCE + ", " + OBJET_STAT_FORCE + ", " + OBJET_STAT_INTELLIGENCE + ", " + OBJET_STAT_SAGESSE + ", "
//                + OBJET_DPS + ", " + OBJET_IMAGE + " ) "
//                + "VALUES ( 'Jambiere', 4, 1, 1, 1, 1, 1, 1, 0, 'jambiere.png' ); "
//        );
//
//        // Botte
//        executeUpdate("" +
//                "INSERT INTO " + NOM_TABLE_OBJET + " ( "
//                + OBJET_NOM + ", " + OBJET_EMPLACEMENT + ", " + OBJET_STAT_AGILITE + ", " +  OBJET_STAT_ARMURE + ", "
//                + OBJET_STAT_ENDURANCE + ", " + OBJET_STAT_FORCE + ", " + OBJET_STAT_INTELLIGENCE + ", " + OBJET_STAT_SAGESSE + ", "
//                + OBJET_DPS + ", " + OBJET_IMAGE + " ) "
//                + "VALUES ( 'Botte', 5, 1, 1, 1, 1, 1, 1, 0, 'botte.png' ); "
//        );
//
//        // Hache
//        executeUpdate("" +
//                "INSERT INTO " + NOM_TABLE_OBJET + " ( "
//                + OBJET_NOM + ", " + OBJET_EMPLACEMENT + ", " + OBJET_STAT_AGILITE + ", " +  OBJET_STAT_ARMURE + ", "
//                + OBJET_STAT_ENDURANCE + ", " + OBJET_STAT_FORCE + ", " + OBJET_STAT_INTELLIGENCE + ", " + OBJET_STAT_SAGESSE + ", "
//                + OBJET_DPS + ", " + OBJET_IMAGE + " ) "
//                + "VALUES ( 'Hache', 6, 1, 1, 1, 1, 1, 1, 2, 'hache.png' ); "
//        );
//
//        System.out.println("Initialisation des objets");

}
