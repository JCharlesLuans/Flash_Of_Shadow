package org.thunderbot.FOS.serveur.networkObject;

import java.io.Serializable;

public class RequeteServeur implements Serializable {

    /* Motif */
    public static final String CHARGEMENT = "chargement";
    public static final String CREATE = "creation";
    public static final String DECONNEXION = "deconnexion";
    public static final String UPDATE = "update";

    /* Objet */
    public static final String MAP ="Map";
    public static final String CLASSE ="Classe";
    public static final String FACTION = "Faction";
    public static final String PERSONNAGE = "Personnage";
    public static final String STUFF_BASE = "stuffDefaut";
    public static final String MOUVEMENT = "move";

    public static final String PNJ = "PersonnageNonJoueur";

    public static final String COMBAT = "fight";
    public static final String START = "start";

    private String requete;

    public RequeteServeur(String requeteServeur) {
        requete = requeteServeur;
    }

    public String getMotif() {
        return requete.split(";")[0];
    }

    public String getObjet() {
        return requete.split(";")[1];
    }

    public String getCle() {
        return requete.split(";")[2];
    }

    @Override
    public String toString() {
        return "RequeteServeur{" +
                "requete='" + requete + '\'' +
                '}';
    }
}
