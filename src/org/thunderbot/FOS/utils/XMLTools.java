/*
 * XMLencoder.java                         16/02/2020
 * Pas de copyright (pour le moment :)
 */

package org.thunderbot.FOS.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * Outil pour manipuler le XML
 *
 * @author Jean-Charles Luans
 * @verison 1.0
 */
public class XMLTools {

    /**
     * Serialisation d'un objet dans un fichier
     *
     * @param object   objet a serialiser
     * @return l'object encoder en XML
     */
    public static String encodeString(Object object) {
        // ouverture de l'encodeur vers le fichier

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        XMLEncoder encoder = new XMLEncoder(byteArrayOutputStream);
        try {
            // serialisation de l'objet
            encoder.writeObject(object);
            encoder.flush();
        } finally {
            // fermeture de l'encodeur
            encoder.close();
        }
        return byteArrayOutputStream.toString();
    }

    /**
     * Deserialisation d'un objet depuis une String
     *
     * @param fileName chemin du fichier
     */
    public static Object decodeString(String fileName) {
        Object object = null;
        // ouverture de decodeur
        try {
            XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(fileName.getBytes()));
            try {
                // deserialisation de l'objet
                object = decoder.readObject();
            } finally {
                // fermeture du decodeur
                decoder.close();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return object;
    }

    /**
     *
     */
    public static String readXMLElement(String documentName, String elementName) {

        String aRetourner = "";

        try {
            File file = new File(documentName);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();

            aRetourner = document.getElementsByTagName(elementName).item(0).getTextContent();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }


        return aRetourner;
    }

}
