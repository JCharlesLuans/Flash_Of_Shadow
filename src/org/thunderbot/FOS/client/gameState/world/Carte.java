package org.thunderbot.FOS.client.gameState.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.thunderbot.FOS.client.gameState.MapGameState;
import org.thunderbot.FOS.client.gameState.entite.PersonnageJoueurClient;
import org.thunderbot.FOS.client.gameState.entite.PersonnageNonJoueur;
import org.thunderbot.FOS.client.network.Client;
import org.thunderbot.FOS.database.beans.Map;
import org.thunderbot.FOS.database.beans.PNJ;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Map et affichage de cette derniere
 */
public class Carte extends Map {

    /** Taille d'une tuille */
    private final int TAILLE_TUILLE = 32;

    private TiledMap tiledMap;

    private String nomMap;

    private ArrayList<PersonnageNonJoueur> listePnj;

    private boolean changeCarte; // Indique si la map change

    private Random rnd = new Random(); // Générateur de aléa

    /**
     * Initialise la map
     * @throws SlickException
     */
    public Carte() throws SlickException {
        initialiseMap("res/carte/map_start_umbra.tmx");
        tiledMap = new TiledMap("res/carte/map_start_umbra.tmx");
        nomMap = "map_campagne_ThunderSun.tmx";
    }

    /**
     * Fait le rendu du foreground de la map
     */
    public void renderBackground() {
        tiledMap.render(0 ,0, 0); // eau
        tiledMap.render(0 ,0, 1); // sol
        tiledMap.render(0 ,0, 2); // background
        tiledMap.render(0 ,0, 3); // background 2
    }

    /**
     * Fait le rendu du foreground de la map
     */
    public void renderForeground() {
        tiledMap.render(0,0,4); // overground
        tiledMap.render(0,0,5); // overground 2
    }

    /**
     * Réecrit le fichier de map pour qu'il sois lisible par Slick
     * (ajoute a l'attribut objectgroup une hauteur et une largeur)
     * @param cheminMap : chemin du fichier map a réécrire
     */
    static void initialiseMap(String cheminMap) {

        ArrayList<String> newMap = new ArrayList<>();

        BufferedReader lecteurAvecBuffer = null;
        PrintWriter ecrivain;

        String ligne;

        try
        {
            lecteurAvecBuffer = new BufferedReader(new FileReader(cheminMap));
            while ((ligne = lecteurAvecBuffer.readLine()) != null) {

                if (ligne.contains("<objectgroup") && !ligne.contains("width")) {
                    System.out.println(ligne);
                    ligne = ligne.replace('>', ' ');
                    if (ligne.contains("/")) {
                        ligne = ligne.replace('/', ' ');
                        ligne = ligne.concat("width=\"1\" height=\"1\" />");
                    } else {
                        ligne = ligne.concat("width=\"1\" height=\"1\" >");
                    }

                }
                newMap.add(ligne);
            }
            lecteurAvecBuffer.close();
        } catch(FileNotFoundException exc) {
            System.out.println("Erreur d'ouverture");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ecrivain =  new PrintWriter(new BufferedWriter
                    (new FileWriter(cheminMap)));

            for (int i = 0; i < newMap.size(); i++) {
                ecrivain.println(newMap.get(i));
            }
            ecrivain.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Permet de recuperer les position des objets colision dans le XML d'une map, afin de pouvoir simuler les
     * deplacement des PNJ
     * @param nomCarte => Nom de la carte dont on cherche a avoir les colisions
     * @return
     */
    public static int[][] getColisionObject(String nomCarte) {
        BufferedReader lecteurAvecBuffer = null;

        String chemin = "res/carte/" + nomCarte;
        String nomBalise = "objectgroup";
        String attribut = "colision";

        int compteurLigne = 0;
        int compteurValeur = 0;
        int[][] aRetourner;
        ArrayList<Integer> valeurs = new ArrayList<>();

        String ligne;
        String resultat = "";

        try {
            lecteurAvecBuffer = new BufferedReader(new FileReader(chemin));
            while ((ligne = lecteurAvecBuffer.readLine()) != null) {

                if (ligne.contains(nomBalise) && ligne.contains(attribut)) {
                    while ((ligne = lecteurAvecBuffer.readLine()) != null && !(ligne.contains("</"))) {
                        resultat += ligne + "\n";

                        compteurLigne++; // Connaitre le nombre de ligne
                    }
                }
            }
            lecteurAvecBuffer.close();
        } catch(FileNotFoundException exc) {
            System.out.println("Erreur d'ouverture");
        } catch (IOException e) {
            e.printStackTrace();
        }

        resultat = resultat.replaceAll("\"", "");
        resultat = resultat.replaceAll("/", " ");
        resultat = resultat.replaceAll("=", " ");

        String[] splited = resultat.split(" ");

        for (String current : splited) {
            try {
                //tenter de convertir le mot en int
                int parsedInt = Integer.parseInt(current);
                //ajouter l Integer à la list
                valeurs.add(parsedInt);                    //un "auto boxing", une instance de Integer est créée à partir d'un int

            } catch (NumberFormatException e) {
                //c est pas un int
            }
        }

        aRetourner = new int[compteurLigne][5];
        for (int i = 0; i < compteurLigne; i++) {
            for (int j = 0; j < 5; j++) {
                aRetourner[i][j] = valeurs.get(compteurValeur);
                compteurValeur++;
            }
            if (compteurValeur > valeurs.size())
                break;
        }

        return aRetourner;
    }

    /**
     * Verifie si il y a une colision
     * @param x axe x sur lequelle on verifie si il y a une colision
     * @param y axe x sur lequelle on verifie si il y a une colision
     * @return true si il y a une colision
     *         false si il n'y a pas de colision
     */
    public boolean isCollision(float x, float y) {

        Image tile;
        Color color;

        /* On va chercher la tile qui se trouve au coordonnée future du personnage sur le calque logic */
        tile = tiledMap.getTileImage((int) x / tiledMap.getTileWidth(),
                (int) y / tiledMap.getTileHeight(),
                tiledMap.getLayerIndex("logique"));

        if (tile != null) {
            //Recupere la couleur
            color = tile.getColor((int) x % tiledMap.getTileHeight(), (int) y % tiledMap.getTileHeight());
            return color.getAlpha() > 0;
        } else {
            return false;
        }
    }

    /**
     * Change de map
     * @param nom du fichier de la nouvelle carte
     * @throws SlickException
     */
    public void changeMap(String nom, Client client) throws SlickException {

        listePnj = new ArrayList<>();
        Map map = client.chargementCarte(nom);
        ArrayList<PNJ> listeDataPnj = client.chargementPnjOnMap(map.getId());
        System.out.println("Chargement de la carte depuis le serveur : " + map);

        for (int i = 0; i < listeDataPnj.size(); i++) {
            listePnj.add(new PersonnageNonJoueur(listeDataPnj.get(i)));
        }


        String chemin = "res/carte/" + nom;
        initialiseMap(chemin);
        tiledMap = new TiledMap(chemin);
        nomMap = nom;
    }

    public void changeMapServeur(String nom) {
        try {
            String chemin = "res/carte/" + nom;
            initialiseMap(chemin);
            tiledMap = new TiledMap(chemin);
            nomMap = nom;
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Verifie si la position en X Y est dans un trigger dont l'id est ID
     * @param positionX position en X
     * @param positionY position en Y
     * @param id id de l'objet
     * @return true si il y a un trigger
     */
    public boolean isInTrigger(float positionX, float positionY, int id) {

        return positionX > getObjectX(id)
                && positionX < getObjectX(id) + getObjectWidth(id)
                && positionY > getObjectY(id)
                && positionY < getObjectY(id) + getObjectHeight(id);
    }


    /*-- Getter et setters --*/

    public int getObjectCount() {
        return tiledMap.getObjectCount(0);
    }

    public String getObjectType(int objectID) {
        return tiledMap.getObjectType(0, objectID);
    }

    public float getObjectX(int objectID) {
        return tiledMap.getObjectX(0, objectID);
    }

    public float getObjectY(int objectID) {
        return tiledMap.getObjectY(0, objectID);
    }

    public float getObjectWidth(int objectID) {
        return tiledMap.getObjectWidth(0, objectID);
    }

    public float getObjectHeight(int objectID) {
        return tiledMap.getObjectHeight(0, objectID);
    }

    public String getObjectProperty(int objectID, String propertyName, String def) {
        return tiledMap.getObjectProperty(0, objectID, propertyName, def);
    }

    public int getWidth() {
        return tiledMap.getWidth();
    }

    public int getHeight() {
        return tiledMap.getHeight();
    }

    public void setChangeCarte(boolean newEtat) {
        changeCarte = newEtat;
    }

    public boolean getChangeCarte() {
        return changeCarte;

    }

    public String getNomMap() {
        return nomMap;
    }


    /**
     * Appeller par le PLAYER CONTROLLER lorsque l'on fait un click pour tester les différente possibiliter de clique sur la
     * carte
     * @param mapGameState
     * @param x position en x du curseur de la souris
     * @param y position en y du curseur de la souris
     * @param personnage personnage qui fait l'action
     */
    public void mouseClicked(PersonnageJoueurClient personnage, MapGameState mapGameState, int x, int y) {
        for (int i = 0; i < listePnj.size(); i++) {
            listePnj.get(i).mouseClicked(personnage, mapGameState, x, y);
        }
    }
}