package org.thunderbot.FOS.serveur;

import org.newdawn.slick.Sound;
import org.thunderbot.FOS.database.FosDAO;
import org.thunderbot.FOS.database.beans.Classe;
import org.thunderbot.FOS.database.beans.Competence;

import java.util.ArrayList;
import java.util.Scanner;

public class CmdServeur extends Thread {

    private static final int CASE_TABLEAU_AFFFICHAGE = 21;

    private static final int STOP_SERVEUR = 0;
    private static final int GESTION_CLASSE = 1;
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
            System.out.println(STOP_SERVEUR       + " : Stop du serveur");
            System.out.println(GESTION_CLASSE     + " : Affichage des classes");
            System.out.println(GESTION_COMPETENCE + " : Affichage des factions");
            System.out.println(GESTION_MAP        + " : Affichage des maps");
            System.out.println(GESTION_OBJET      + " : Affichage des objets");
            System.out.println(GESTION_PNJ        + " : Affichage des pnj");

            switch (saisie()) {
                case STOP_SERVEUR:
                    serveurOn = false;
                    break;
                case GESTION_CLASSE:
                    affichageClasse();
                    break;
                case GESTION_COMPETENCE:
                    affichageCompetence();
                    break;
                case GESTION_FACTION:
                    break;
                case GESTION_MAP:
                    break;
                case GESTION_OBJET:
                    break;
                case GESTION_PNJ:
                    break;
                default:
                    System.out.println("Commande inconnu");
            }
        }
    }

    private int saisie() {
        Scanner scanner = new Scanner(System.in);
        boolean saisieNok = true;
        int aRetourner = -1;
        do {
            System.out.print("Saisissez une option\n>>> ");
            if (scanner.hasNextInt()) {
                aRetourner = scanner.nextInt();
                saisieNok = false;
            } else {
                scanner.next();
            }
        } while (saisieNok);

        return aRetourner;
    }

    /**
     * Permet d'afficher toute les classes sur la sortie stendart
     */
    private void affichageClasse() {

        ArrayList listeAAfficher = accesBD.getClasseAll();

        System.out.println("Affichage des classes");
        System.out.println("|---------------------|---------------------|---------------------|---------------------|---------------------|");
        System.out.println("|         ID          |         NOM         |       AGILITE       |       ENDURANCE     |         FORCE       |     INTELLIGENCE    |        SAGESSE      |");
        System.out.println("|---------------------|---------------------|---------------------|---------------------|---------------------|---------------------|---------------------|");
        for (int i = 0; i < listeAAfficher.size(); i++) {
            Classe tmp = (Classe) listeAAfficher.get(i);
            System.out.print(formatageText("" + tmp.getId()));
            System.out.print(formatageText("" +tmp.getNom()));
            System.out.print(formatageText("" + tmp.getStatAgilite()));
            System.out.print(formatageText("" + tmp.getStatEndurance()));
            System.out.print(formatageText("" + tmp.getStatForce()));
            System.out.print(formatageText("" + tmp.getStatIntelligence()));
            System.out.print(formatageText("" + tmp.getStatSagesse()));
            System.out.println("|");
        }
        System.out.println("|---------------------|---------------------|---------------------|---------------------|---------------------|");
    }

    private void affichageCompetence() {

        ArrayList listeAAfficher = accesBD.getCompetenceAll();

        System.out.println("Affichage des classes");
        System.out.println("|---------------------|---------------------|---------------------|---------------------|---------------------|");
        System.out.println("|         ID          |         NOM         |        DEGÂTS       |        ID EFFET     |        ID IMAGE     |");
        System.out.println("|---------------------|---------------------|---------------------|---------------------|---------------------|");
        for (int i = 0; i < listeAAfficher.size(); i++) {
            Competence tmp = (Competence) listeAAfficher.get(i);
            System.out.print(formatageText("" + tmp.getId()));
            System.out.print(formatageText("" + tmp.getNom()));
            System.out.print(formatageText("" + tmp.getDegaBase()));
            System.out.print(formatageText("" + tmp.getIdEffet()));
            System.out.print(formatageText("" + tmp.getImage()));
            System.out.println("|");
        }
        System.out.println("|---------------------|---------------------|---------------------|---------------------|---------------------|");
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
