package org.thunderbot.FOS.serveur;

import org.thunderbot.FOS.database.FosDAO;
import org.thunderbot.FOS.database.HelperBD;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class CmdServeur extends Thread {

    private String[] listeCommande;
    private String[] descriptionCommande;
    private static final int NOMBRE_COMMANDES = 3;

    private static final String CMD_EXECUTE_SQL = "exec sql";
    private static final String DESC_EXECUTE_SQL = "Permet d'executer une requetes SQL sur la base de donnée du serveur";
    private static final String CMD_STOP_SERVEUR = "shutdown";
    private static final String DESC_STOP_SERVEUR = "Permet d'éteindre le serveur.";
    private static final String CMD_HELP = "help";
    private static final String DESC_HELP = "Affiche les commandes serveur";


    private static final int CASE_TABLEAU_AFFFICHAGE = 21;
    private static final int GESTION_COMPETENCE = 2;
    private static final int GESTION_FACTION = 3;
    private static final int GESTION_MAP = 4;
    private static final int GESTION_OBJET = 5;
    private static final int GESTION_PNJ = 6;

    public boolean serveurOn = true;
    public FosDAO accesBD;

    public CmdServeur(FosDAO accesBD) {
        this.accesBD = accesBD;

        // Gestion des commandes
        listeCommande = new String[NOMBRE_COMMANDES];
        descriptionCommande= new String[NOMBRE_COMMANDES];

        listeCommande[0] = CMD_STOP_SERVEUR;
        descriptionCommande[0] = DESC_STOP_SERVEUR;
        listeCommande[1] = CMD_EXECUTE_SQL;
        descriptionCommande[1] = DESC_EXECUTE_SQL;
        listeCommande[2] = CMD_HELP;
        descriptionCommande[2] = DESC_HELP;
    }

    public void run() {

        System.out.println("Lancement du serveur");

        while (serveurOn) {

            switch (saisie()) {
                case CMD_STOP_SERVEUR:
                    serveurOn = false;
                    break;
                case CMD_EXECUTE_SQL:
                    executerSQL();
                    break;
                case CMD_HELP:
                    afficherAide();
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

    private void afficherAide() {
        for (int i = 0; i < NOMBRE_COMMANDES; i++) {
            System.out.println(listeCommande[i] + " | "  + descriptionCommande[i]);
        }
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
