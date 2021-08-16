/*
 * tests.java             26/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS;

import org.newdawn.slick.SlickException;
import org.thunderbot.FOS.client.gameState.entite.ServPersonnage;
import org.thunderbot.FOS.utils.XMLTools;

/**
 * Classe de test pour XML et autres utils
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class tests {
    public static void main(String[] args) throws SlickException {
        ServPersonnage aSerialiser = new ServPersonnage("JS", 7, 300f, 300f);

        String xml = XMLTools.encodeString(aSerialiser);
        System.out.println(xml);

        Object aAfficher = XMLTools.decodeString(xml);

        System.out.println(aAfficher.getClass());

    }
}
