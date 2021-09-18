/*
 * Update.java             27/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.serveur.networkObject;

import org.thunderbot.FOS.database.beans.Personnage;

import java.io.Serializable;

/**
 * Donnée de mise a jour
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Update implements Serializable {

    private Personnage personnage;

    public Update() {
        personnage = null;
    }

    public Update(Personnage personnage) {
        this.personnage = personnage;
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    public void setPersonnage(Personnage personnage) {
        this.personnage = personnage;
    }
}
