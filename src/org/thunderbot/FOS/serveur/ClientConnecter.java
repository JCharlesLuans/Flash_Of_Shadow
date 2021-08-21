/*
 * ClientConnecter.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.serveur;

import org.thunderbot.FOS.client.gameState.entite.ServPersonnage;

import java.awt.event.ActionListener;
import java.net.InetAddress;

/**
 * Client connecter au serveur. Definis par son pseudo
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class ClientConnecter {
    private InetAddress address;
    private ServPersonnage servPersonnage;

    public ClientConnecter(InetAddress address) {
        this.address = address;
        servPersonnage = new ServPersonnage();
        servPersonnage.setNomCarte("");
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public ServPersonnage getServPersonnage() {
        return servPersonnage;
    }

    public void setServPersonnage(ServPersonnage servPersonnage) {
        this.servPersonnage = servPersonnage;
    }
}
