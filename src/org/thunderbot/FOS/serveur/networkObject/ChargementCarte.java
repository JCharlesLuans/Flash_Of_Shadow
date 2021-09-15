package org.thunderbot.FOS.serveur.networkObject;

import org.thunderbot.FOS.database.beans.Map;
import org.thunderbot.FOS.database.beans.Personnage;

import java.io.Serializable;

public class ChargementCarte implements Serializable {

    private Map map;

    public ChargementCarte(String nom) {
        map = new Map();
        map.setNom(nom);
    }

    public Map getCarte() {
        return map;
    }
}
