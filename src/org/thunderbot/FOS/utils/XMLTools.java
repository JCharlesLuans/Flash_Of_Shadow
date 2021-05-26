/*
 * XMLencoder.java                         16/02/2020
 * Pas de copyright (pour le moment :)
 */

package org.thunderbot.FOS.utils;

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
     * @param fileName chemin du fichier
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
}
