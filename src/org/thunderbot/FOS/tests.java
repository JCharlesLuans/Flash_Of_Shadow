/*
 * tests.java             26/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS;

import org.newdawn.slick.SlickException;
import org.thunderbot.FOS.client.gameState.entite.PersonnageJoueur;
import org.thunderbot.FOS.utils.Tools;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Classe de test pour XML et autres utils
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class tests {
    public static void main(String[] args) {
        try {
            //testSerialisation();
            testLectureFichierXML();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public static void testLectureFichierXML() throws ParserConfigurationException, IOException, SAXException {
        System.out.println(Tools.readXMLElement("ipServeur", "res/option.xml"));
    }
}
