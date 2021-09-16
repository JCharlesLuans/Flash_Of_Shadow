package org.thunderbot.FOS.serveur.networkObject;

import java.io.Serializable;

public class RequeteServeur implements Serializable {

    public static final String CHARGEMENT = "chargement";

    public static final String MAP ="map";
    public static final String CLASSE ="classe";
    public static final String FACTION = "faction";

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
}
