/*
 * org.thunderbot.FOS.Client.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.network;

import org.thunderbot.FOS.database.beans.*;
import org.thunderbot.FOS.serveur.networkObject.Authentification;
import org.thunderbot.FOS.serveur.networkObject.RequeteServeur;
import org.thunderbot.FOS.utils.Tools;

import javax.swing.*;
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
     * Envoi sa propre update puis demande demande de update au serveur. Le serveur renvoie d'abord une update des joueurs
     * par la suite
     */
    public ArrayList<Personnage> updateServeurMouvementJoueurs() {
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
     * Demande de update au serveur. Le serveur renvoie alors une update des PNJ
     * par la suite
     */
    public ArrayList<PNJ> updateServeurMouvementPNJ() {
        String requete = RequeteServeur.UPDATE + ";" + RequeteServeur.PNJ + ';';
        String stringReception;
        ArrayList<PNJ> listePersonnageAJour = new ArrayList<>();

        try {
            envoi(new RequeteServeur(requete));
            stringReception = Tools.encodeString(personnage);
            envoi(stringReception);
            stringReception = (String) reception(); // Attente reception update
            listePersonnageAJour = (ArrayList<PNJ>) Tools.decodeString(stringReception);

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

    /**
     * Envoi une demande au serveur pour charger les information relative
     * à une classe, dont le nom est passé en paramettre.
     * @param nomClasse Nom de la classe dont on veux les information
     * @return l'objet classe encapsulant les infos.
     */
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

    /**
     * Envoi une demande au serveur pour charger les information relative
     * à une faction, dont le nom est passé en paramettre.
     * @param nom Nom de la faction dont on veux les informations
     * @return l'objet Faction encapsulant les infos.
     */
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

    /**
     * Envoi une demande au serveur pour charger les information relative
     * à 6 objet, du 2eme au 7eme, ce qui correspond à l'équipement de départ d'un nouveau joueur.
     * Les objet vont directement ètre appliquer au personnage de ce client.
     */
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

    /**
     * Envooi une demande au serveur pour charger les inforamtions relative aux personnages non joueur
     * se trouvant sur une carte. Le serveur renvoi un objet arrayliste contant ces PNJ.
     * @param idMap id de la carte dont on veux les PNJ
     * @return une array list contenant les différent PNJ
     */
    public ArrayList<PNJ> chargementPnjOnMap(int idMap) {
        ArrayList<PNJ> aRetourner = new ArrayList<>();
        String requete = RequeteServeur.CHARGEMENT + ";" + RequeteServeur.PNJ + ";" + RequeteServeur.MAP + ":" + idMap;

        try {
            envoi(new RequeteServeur(requete));
            aRetourner = (ArrayList<PNJ>) Tools.decodeString((String) reception());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return aRetourner;
    }

    /**
     * Envoi un demande au serveur pour créer un personnage, puis envoi le personnage de se client. Le serveur revoi
     * ensuite l'ID du personnage.
     */
    public void createPersonnage() {
        String requete = RequeteServeur.CREATE + ";" + RequeteServeur.PERSONNAGE + ";";

        try {
            envoi(new RequeteServeur(requete));
            envoi(personnage);

            personnage = (Personnage) reception();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String lectureIpServeur() {
        return Tools.readXMLElement("res/option.xml", "ipServeur");
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

    /**
     * Gere l'envoi d'objet par socket
     * @param objet a envoyer
     */
    private void envoi(Object objet) throws IOException {
        sortie.writeObject(objet);
        sortie.flush();
    }

    /**
     * Serialise un objet en XML et l'envoi au serveur
     * @param objet à serialiser et envoyé
     * @throws IOException
     */
    private void envoiXML(Object objet) {
        try {
            sortie.writeObject(Tools.encodeString(objet));
            sortie.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Serveur déconnecté!");
        }

        return aRetourner;
    }

    private Object receptionXML() {
        Object aRetourner = new Object();
        String reception;
        try {
            reception = (String) entree.readObject();
            aRetourner = Tools.decodeString(reception);
        } catch (ClassNotFoundException | IOException e) {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Serveur déconnecté!");
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

    /**
     * Envoi au serveur qu'un combat à été déclancher entre le joueur et le mobs.
     * @param pnjEncode
     */
    public void entreeServeurCombat(String pnjEncode) {
        try {
            //Envoi au serveur que le joueur et le PNJ rentre en combat
            String requete = RequeteServeur.UPDATE + ";" + RequeteServeur.COMBAT + ';' + RequeteServeur.START;
            envoi(new RequeteServeur(requete));

            envoi(pnjEncode);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Met a jour les donnée de combats et les synchronise avec le serveur
     * @param personnage personnage a mettre a jour
     * @param listePNJ PNJs a mettre a jour
     */
    public void updateServeurCombat(Personnage personnage, ArrayList<PNJ> listePNJ) {

        ArrayList<PNJ> tmpListe;
        Personnage tmpPersonnage;
        // envoi requete pour prevenir qu'on met a jour le combat
        String requete = RequeteServeur.UPDATE + ";" + RequeteServeur.COMBAT + ';' + RequeteServeur.UPDATE;
        try {
            envoi(new RequeteServeur(requete));

            // envoi nouvelle position du joueur;
            envoiXML(personnage);
            // envoi etat pnj
            envoiXML(listePNJ);

            // reception nouvelle position des PNJ
            tmpListe = (ArrayList<PNJ>) receptionXML();

            for (int i = 0; i < tmpListe.size(); i++) {
                listePNJ.set(i, tmpListe.get(i));
            }

            // reception etat du joueur
            tmpPersonnage = (Personnage) receptionXML();

            personnage.setData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Met a jour les donnée de combats et les synchronise avec le serveur a la fin de combat
     * @param personnage personnage a mettre a jour
     * @param listePNJ PNJs a mettre a jour
     */
    public void finServeurCombat(Personnage personnage, ArrayList<PNJ> listePNJ) {

        ArrayList<PNJ> tmpListe;
        Personnage tmpPersonnage;
        // envoi requete pour prevenir qu'on met a jour le combat
        String requete = RequeteServeur.UPDATE + ";" + RequeteServeur.COMBAT + ';' + RequeteServeur.END;
        try {
            envoi(new RequeteServeur(requete));

            // envoi nouvelle position du joueur;
            envoiXML(personnage);

            // envoi etat pnj
            envoiXML(listePNJ);
//
//            // reception nouvelle position des PNJ
//            tmpListe = (ArrayList<PNJ>) receptionXML();
//
//            for (int i = 0; i < tmpListe.size(); i++) {
//                listePNJ.set(i, tmpListe.get(i));
//            }
//
//            // reception etat du joueur
//            tmpPersonnage = (Personnage) receptionXML();
//
//            personnage.setData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Récupere la liste des PNJs depuis le serveur
     * @return la liste
     */
    public ArrayList<PNJ> initialiseCombatPNJ() {
        ArrayList<PNJ> aRetourner = new ArrayList<>();
        String aDecoder;
        aDecoder = (String) reception();
        aRetourner = (ArrayList<PNJ>) Tools.decodeString(aDecoder);
        return aRetourner;
    }

    /**
     * Envoi les donnée du personnage au serveur pour effectuer la sauvegarde au début du combat
     * @return le personnage renvoyé par le serveur avec les position relative au combat game state
     */
    public Personnage initialiseCombatJoueur() {

        envoiXML(personnage);
        return (Personnage) receptionXML();
    }
}
