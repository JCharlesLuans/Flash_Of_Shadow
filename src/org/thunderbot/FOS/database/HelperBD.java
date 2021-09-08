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

    Connection connection;
    Statement statement;

    public HelperBD(String nomBD) {
        open(nomBD);
        executeUpdate(CREATION_TABLE_JOUEUR);
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
            statement.close();
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
            statement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return rs;
    }



    private void initDataJoueur() {
        executeUpdate(
                "INSERT INTO JOUEUR (" + JOUEUR_PSEUDO + ","+ JOUEUR_MDP + ")"
                + "VALUES           (    \"Jean Test\",           \"leserveur\"     );"
        );
    }


}
