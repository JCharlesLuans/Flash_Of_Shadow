package org.thunderbot.FOS.serveur;

import org.newdawn.slick.Sound;
import org.thunderbot.FOS.database.FosDAO;
import org.thunderbot.FOS.database.HelperBD;
import org.thunderbot.FOS.database.beans.Classe;
import org.thunderbot.FOS.database.beans.Competence;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import static org.thunderbot.FOS.database.HelperBD.*;

public class CmdServeur extends Thread {

    private static final int CASE_TABLEAU_AFFFICHAGE = 21;

    private static final String STOP_SERVEUR = "shutdown";
    private static final String EXECUTE_SQL = "exec sql";
    private static final int GESTION_COMPETENCE = 2;
    private static final int GESTION_FACTION = 3;
    private static final int GESTION_MAP = 4;
    private static final int GESTION_OBJET = 5;
    private static final int GESTION_PNJ = 6;

    public boolean serveurOn = true;
    public FosDAO accesBD;

    public CmdServeur(FosDAO accesBD) {
        this.accesBD = accesBD;
    }

    public void run() {

        System.out.println("Lancement du serveur");

        while (serveurOn) {

            switch (saisie()) {
                case STOP_SERVEUR:
                    serveurOn = false;
                    break;
                case EXECUTE_SQL:
                    executerSQL();
                    break;
                default:
                    System.out.println("Commande inconnu");
            }
        }

        System.out.println("Arret du serveur en cours ...");
        System.exit(1);
    }

    private void executerSQL() {
        HelperBD helper = accesBD.getGestionnaireBase();
        System.out.print("Saisissez votre requete : ");
        String requete = saisie();

        try {
            if (requete.split(" ")[0].equalsIgnoreCase("select")) {
                executionRequete(helper, requete);
            } else {
                executionUpdate(helper, requete);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void executionRequete(HelperBD helper, String requete) throws SQLException {
        ResultSet rs = helper.executeRequete(requete);

        if (rs != null) {

            ResultSetMetaData rsmd = rs.getMetaData();

            // Dessin entete du tableau
            printLigne(rsmd.getColumnCount());
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.print(formatageText(rsmd.getColumnName(i)));
            }
            System.out.print("|\n");
            printLigne(rsmd.getColumnCount());

            // Affichage des valeurs de la BD
            while (rs.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    System.out.print(formatageText(rs.getString(i)));
                }
                System.out.print("|\n");
            }
            rs.close();
        }
    }

    private void executionUpdate(HelperBD helper, String requete) {
        helper.executeUpdate(requete);
    }

    private void printLigne(int columnCount) {
        String ligne = "|";
        for (int i = 0; i < CASE_TABLEAU_AFFFICHAGE; i++) {
            ligne += '-';
        }
        
        for (int i = 0; i < columnCount; i++) {
            System.out.print(ligne);
        }

        System.out.println("|");
    }

    private String saisie() {
        Scanner scanner = new Scanner(System.in);
        boolean saisieNok = true;
        String aRetourner = "";
        do {
            System.out.print(">>> ");
            if (scanner.hasNextLine()) {
                aRetourner = scanner.nextLine();
                saisieNok = false;
            } else {
                scanner.next();
            }
        } while (saisieNok);

        return aRetourner;
    }

    private String formatageText(String texte) {
        boolean isPair;
        String espace = "";
        int nombreEspace = 0;
        isPair = texte.length() % 2 ==0;

        if (isPair) {
            texte += " ";
        }

        if (texte.length() > CASE_TABLEAU_AFFFICHAGE) {
            texte = texte.substring(0, CASE_TABLEAU_AFFFICHAGE - 2);
        }

        nombreEspace = (CASE_TABLEAU_AFFFICHAGE - texte.length()) / 2;

        for (int i = 0; i < nombreEspace; i++) {
            espace += " ";
        }

        return "|" + espace + texte + espace;
    }
}
