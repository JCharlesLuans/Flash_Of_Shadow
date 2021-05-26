/*
 * tests.java             26/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS;

import org.thunderbot.FOS.client.gameState.entite.ServPersonnage;
import org.thunderbot.FOS.utils.XMLTools;

/**
 * TODO decrire la classe
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class tests {
    public static void main(String[] args) {
        ServPersonnage aSerialiser = new ServPersonnage("JS", 7, 300f, 300f);

        String xml = XMLTools.encodeString(aSerialiser);
        System.out.println(xml);

        ServPersonnage aAfficher = (ServPersonnage) XMLTools.decodeString(xml);
        System.out.println(aAfficher.toString());

    }
}
