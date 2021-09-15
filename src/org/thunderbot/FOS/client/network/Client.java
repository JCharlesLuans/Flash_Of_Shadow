/*
 * org.thunderbot.FOS.Client.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.network;

import org.thunderbot.FOS.client.gameState.entite.ServPersonnage;
import org.thunderbot.FOS.database.beans.Joueur;
import org.thunderbot.FOS.database.beans.Personnage;
import org.thunderbot.FOS.serveur.networkObject.Authentification;
import org.thunderbot.FOS.utils.XMLTools;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

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
        } else {
            personnage = (Personnage) reception();

            //TODO ed
            System.out.println(personnage.toString());

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

//    /**
//     * Dconnecte le client auprés du serveur
//     * @throws IOException
//     */
//    public void deconnexion() throws IOException {
//        envoi(XMLTools.encodeString(new Stop(pseudo)));
//    }
//
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
        try {
            return entree.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return null;
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
//    /**
//     * Renvoi les donnée actuelle du client au serveur pour actualiser les autres clients
//     * @param mapGameState - State mise a jour
//     */
//    public void updateServeur(MapGameState mapGameState) {
//        try {
//            Update tmp = new Update(mapGameState.getJoueur());
//            tmp.setMap(mapGameState.getJoueur().getNomCarte());
//            envoi(XMLTools.encodeString(tmp));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    private String lectureIpServeur() {
        return XMLTools.readXMLElement("res/option.xml", "ipServeur");
    }
}
