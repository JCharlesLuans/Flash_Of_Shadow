/*
 * org.thunderbot.FOS.Client.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.network;

import org.thunderbot.FOS.client.gameState.entite.ServPersonnage;
import org.thunderbot.FOS.database.beans.*;
import org.thunderbot.FOS.serveur.networkObject.Authentification;
import org.thunderbot.FOS.serveur.networkObject.RequeteServeur;
import org.thunderbot.FOS.serveur.networkObject.Stop;
import org.thunderbot.FOS.serveur.networkObject.Update;
import org.thunderbot.FOS.utils.XMLTools;

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
    public Client() throws IOException {
        InetAddress adresseServeur = InetAddress.getByName(lectureIpServeur());
        socket = new Socket(adresseServeur, PORT);

        entree = new ObjectInputStream(socket.getInputStream());
        sortie = new ObjectOutputStream(socket.getOutputStream());
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

        if (nouveauJoueur) {

            personnage = new Personnage();
            Joueur tmp = (Joueur) reception();
            personnage.setIdJoueur(tmp.getId());

            // TODO ED
            System.out.println("Création du nouveau joueur");
            System.out.println(personnage.getIdJoueur());


        } else if (code == 0) {
            //TODO ed
            System.out.println("Attente personnage");
            personnage = (Personnage) reception();



        }


        return code;
    }

    /**
     * Charge un personnage suite a une connexion
     * @return
     */
    public ServPersonnage loadJoueur() {
        return (ServPersonnage) reception();
    }

    /**
     * Dconnecte le client auprés du serveur
     * @throws IOException
     */
    public void deconnexion() throws IOException {
        envoi(new Stop(personnage));
    }

//    /**
//     * Actualise les données du client
//     * @param mapGameState
//     */
//    public void updateClient(MapGameState mapGameState) {
//        new Thread(actualisationDonneeDistante(mapGameState.getListeJoueur())).start();
//    }

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

//    /**
//     * Reception et actualisation des donnée sur le client de jeu
//     * @param listeJoueur liste des joueur a actualiser
//     * @return un thread
//     */
//    private Runnable actualisationDonneeDistante(ArrayList<ServPersonnage> listeJoueur) {
//        return () -> {
//
//
//            // Joueur a actualiser existe ou pas
//            boolean existe;
//
//            while (true) {
//
//                existe = false;
//
//                try {
//
//                    String reception = reception();
//
//                    Object objReception = XMLTools.decodeString(reception);
//
//                    // Update
//                    if (objReception.getClass() == Update.class) {
//                        Update update = (Update) objReception;
//                        ServPersonnage tmp = update.getServPersonnage();
//
//                        // Mise a jour des joueur qui existe
//                        for (int i = 0; i < listeJoueur.size(); i++) {
//
//                            if (listeJoueur.get(i).getPseudo().equals(tmp.getPseudo())) {
//
//                                existe = true;
//
//                                // Mise à jours du joueur
//                                listeJoueur.get(i).miseAJour(tmp);
//                            }
//                        }
//
//                        // Ajout du nouveau joueur a la liste
//                        if (!existe) {
//                            listeJoueur.add(new ServPersonnage(tmp));
//                        }
//
//                    } else if (objReception.getClass() == Stop.class) {
//
//                        // Deconnexion
//                        Stop tmp = (Stop) objReception;
//
//                        // Recherche du joueur a remove
//                        for (int i = 0; i < listeJoueur.size(); i++) {
//
//                            if (listeJoueur.get(i).getPseudo().equals(tmp.getPseudo())) {
//
//                                // Suppression de l'ancien puis remplacement
//                                listeJoueur.remove(i);
//                                System.out.println("Deconexion de " + tmp.getPseudo());
//                            }
//                        }
//                    }
//
//                } catch(IOException | SlickException err){
//                    err.printStackTrace();
//                }
//            }
//        };
//    }
//
    /**
     * Renvoi les donnée actuelle du client au serveur pour actualiser les autres clients
     */
    public void updateServeur() {
        try {
            Update update = new Update(personnage);
            envoi(update);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public ArrayList<Classe> chargementListeClasse() {
        ArrayList<Classe> aRetourner = null;
        String requete = RequeteServeur.CHARGEMENT + ";" + RequeteServeur.CLASSE + ";";

        try {
            envoi(new RequeteServeur(requete));
            aRetourner = (ArrayList<Classe>) reception();
         } catch (IOException err) {
            err.printStackTrace();
        }

        return aRetourner;
    }

    public ArrayList<Faction> chargementListeFaction() {
        ArrayList<Faction> aRetourner = null;
        String requete = RequeteServeur.CHARGEMENT + ";" + RequeteServeur.FACTION + ";";

        try {
            envoi(new RequeteServeur(requete));
            aRetourner = (ArrayList<Faction>) reception();
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
        return XMLTools.readXMLElement("res/option.xml", "ipServeur");
    }
}
