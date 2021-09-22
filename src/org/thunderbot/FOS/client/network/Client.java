/*
 * org.thunderbot.FOS.Client.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.network;

import org.thunderbot.FOS.client.gameState.entite.PersonnageJoueur;
import org.thunderbot.FOS.database.beans.*;
import org.thunderbot.FOS.serveur.networkObject.Authentification;
import org.thunderbot.FOS.serveur.networkObject.RequeteServeur;
import org.thunderbot.FOS.utils.Tools;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;

/**
 * Communique avec le serveur pour tenir a jour le client
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Client {

    public static final int PORT = 6700;

    private String pseudo;

    private Personnage personnage;

    private Socket socket;

    private ObjectOutputStream sortie; // Sortie du socket

    private ObjectInputStream entree;   // Entrée du socket


    /**
     * Création de l'objet client, encapsulation de la socket, gestion de la connection avec le serveur
     */
    public Client() {
    }

    /**
     * Authentification du client auprés du serveur
     * @param pseudo du client
     * @param  mdp du client
     * @param nouveauJoueur indique si le joueur est en train de s'inscrire ou de se connecter
     * @throws IOException
     */
    public int authentification(String pseudo, String mdp, boolean nouveauJoueur) throws IOException {
        int code; // Retour

        // TODO ECHAPER CHAR SPECIAUX SQL

        envoi(new Authentification(pseudo, mdp, nouveauJoueur));
        code = (int) reception();

        if (nouveauJoueur && code == 0) {
            /* Inscription */
            personnage = new Personnage();
            Joueur tmp = (Joueur) reception();
            personnage.setIdJoueur(tmp.getId());

        } else if (code == 0) {
            /* Connexion */
            personnage = (Personnage) reception();
        }


        return code;
    }

    /**
     * Dconnecte le client auprés du serveur
     * @throws IOException
     */
    public void deconnexion() throws IOException {
        String requete = RequeteServeur.DECONNEXION + ";" ;
        envoi(new RequeteServeur(requete));

        // Decomposition de l'element personnage pour l'envoyer objet par objet
        // Pour le sauvegarder coter serveur.
        envoi(personnage.getId());
        envoi(personnage.getX());
        envoi(personnage.getY());
        envoi(personnage.getMap());
        envoi(personnage);

        socket.close();

    }

    /**
     * @param objet a envoyer
     */
    private void envoi(Object objet) throws IOException {
        sortie.writeObject(objet);
        sortie.flush();
    }

    /**
     * Receptionne des donnée du serveurs
     * @return l'objets reçu
     */
    private Object reception() {
        Object aRetourner = new Object();
        try {
            aRetourner = entree.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return aRetourner;
    }

    /**
     * Envoi une demande de update au serveur, ainsi que sa propre update
     */
    public ArrayList<Personnage> updateServeurMouvement() {
        String requete = RequeteServeur.UPDATE + ";" + RequeteServeur.MOUVEMENT + ';';
        String stringReception;
        ArrayList<Personnage> listePersonnageAJour = new ArrayList<>();

        try {
            envoi(new RequeteServeur(requete));
            stringReception = Tools.encodeString(personnage);
            envoi(stringReception);
            stringReception = (String) reception(); // Attente reception update
            listePersonnageAJour = (ArrayList<Personnage>) Tools.decodeString(stringReception);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listePersonnageAJour;
    }

    /**
     * Envoi une demande au serveur pour charger les information relative
     * à une carte dont le nom est passer en paramettre
     * @param nom de la carte
     * @return l'objet Map contenant les information
     */
    public Map chargementCarte(String nom) {
        String requete = RequeteServeur.CHARGEMENT + ";" + RequeteServeur.MAP + ";" + nom + ';';
        Map aRetourner = new Map();
        try {
            envoi(new RequeteServeur(requete));
            aRetourner = (Map) reception();
            personnage.setMap(aRetourner);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return aRetourner;
    }

    public Classe chargementClasse(String nomClasse) {
        Classe aRetourner = null;
        String requete = RequeteServeur.CHARGEMENT + ";" + RequeteServeur.CLASSE + ";" + nomClasse + ';';

        try {
            envoi(new RequeteServeur(requete));
            aRetourner = (Classe) reception();
         } catch (IOException err) {
            err.printStackTrace();
        }

        return aRetourner;
    }

    public Faction chargementFaction(String nom) {
        Faction aRetourner = null;
        String requete = RequeteServeur.CHARGEMENT + ";" + RequeteServeur.FACTION + ";" + nom + ';';

        try {
            envoi(new RequeteServeur(requete));
            aRetourner = (Faction) reception();
        } catch (IOException err) {
            err.printStackTrace();
        }

        return aRetourner;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    private String lectureIpServeur() {
        return Tools.readXMLElement("res/option.xml", "ipServeur");
    }

    public void createPersonnage() {
        String requete = RequeteServeur.CREATE + ";" + RequeteServeur.PERSONNAGE + ";";

        try {
            envoi(new RequeteServeur(requete));
            envoi(personnage);

            personnage.setId((int) reception());
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chargementStuffBase() {
        String requete = RequeteServeur.CHARGEMENT + ";" + RequeteServeur.STUFF_BASE + ";";

        try {
            envoi(new RequeteServeur(requete));
            personnage.setStuffTete((Objet) Tools.decodeString((String) reception()));
            personnage.setStuffTorse((Objet) Tools.decodeString((String) reception()));
            personnage.setStuffGant((Objet) Tools.decodeString((String) reception()));
            personnage.setStuffJambe((Objet) Tools.decodeString((String) reception()));
            personnage.setStuffBotte((Objet) Tools.decodeString((String) reception()));
            personnage.setStuffArme((Objet) Tools.decodeString((String) reception()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean socketIsClosed() {
        return socket.isClosed();
    }

    public void connectionServeur() throws IOException {
        InetAddress adresseServeur = null;
            adresseServeur = InetAddress.getByName(lectureIpServeur());
            socket = new Socket(adresseServeur, PORT);
            entree = new ObjectInputStream(socket.getInputStream());
            sortie = new ObjectOutputStream(socket.getOutputStream());
    }
}
