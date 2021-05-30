/*
 * Update.java             27/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.serveur.beans;

import org.thunderbot.FOS.client.gameState.entite.ServPersonnage;

import java.io.Serializable;

/**
 * Donnée de mise a jour
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Update implements Serializable {
    private ServPersonnage servPersonnage;

    public Update() {
        servPersonnage = null;
    }

    public Update(ServPersonnage newServPersonnage) {
        System.out.println("ServPersonnage : " + newServPersonnage);
        servPersonnage = newServPersonnage;
    }

    public ServPersonnage getServPersonnage() {
        return servPersonnage;
    }

    public void setServPersonnage(ServPersonnage servPersonnage) {
        this.servPersonnage = servPersonnage;
    }
}
