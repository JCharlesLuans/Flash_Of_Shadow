/*
 * Stop.java             27/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.serveur.networkObject;

import org.thunderbot.FOS.database.beans.Personnage;

import java.io.Serializable;

/**
 * Classe de deconexion
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Stop implements Serializable {
    private Personnage personnage;

    public Stop() {
        personnage = null;
    }

    public Stop(Personnage personnage) {
        this.personnage = personnage;
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    public void setPersonnage(String pseudo) {
        this.personnage = personnage;
    }
}
