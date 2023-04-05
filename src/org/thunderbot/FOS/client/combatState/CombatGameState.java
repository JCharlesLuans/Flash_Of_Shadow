/*
 * CombatGameState.java             24/09/2021
 * Copyright et copyleft TNLag Corp.
 */
package org.thunderbot.FOS.client.combatState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.thunderbot.FOS.client.gameState.MapGameState;
import org.thunderbot.FOS.client.gameState.entite.PersonnageJoueurClient;
import org.thunderbot.FOS.client.gameState.entite.PersonnageNonJoueur;
import org.thunderbot.FOS.client.gameState.phisique.CombatController;
import org.thunderbot.FOS.client.gameState.world.Carte;
import org.thunderbot.FOS.client.gameState.world.Terrain;
import org.thunderbot.FOS.client.network.Client;
import org.thunderbot.FOS.database.beans.Competence;
import org.thunderbot.FOS.database.beans.Effet;
import org.thunderbot.FOS.database.beans.PNJ;
import org.thunderbot.FOS.database.beans.Personnage;

import java.util.ArrayList;

/**
 * State des combats
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class CombatGameState extends BasicGameState {

    public static final int ID = 4;

    public static final int TAILLE_CASE = 64;
    public static final int TAILLE_INTERFACE = 3;

    private Image background;

    private InterfaceJoueur interfaceJoueur;
    private Client client;

    private CombatController combatController;

    private Carte carte;

    private Terrain terrain;

    private StateBasedGame stateBasedGame;

    int nombreCaseHauteur;

    /** Entité en combats */
    private ArrayList<PersonnageNonJoueur> listePNJAAfficher;
    private PersonnageJoueurClient personnageAAfficher;

    public CombatGameState(Client client) throws SlickException {
        background = new Image("res/combatState/fond.png");
        this.client = client;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        combatController = new CombatController(this);
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        gameContainer.getInput().removeAllKeyListeners();
        gameContainer.getInput().removeAllControllerListeners();
        gameContainer.getInput().removeAllMouseListeners();

        gameContainer.getInput().addControllerListener(combatController);
        gameContainer.getInput().addMouseListener(combatController);
        gameContainer.getInput().addKeyListener(combatController);

        this.stateBasedGame = stateBasedGame;

        terrain = new Terrain();

        initPNJ();
        initJoueur();
        initInterface(gameContainer);
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        background.draw(0, 0, gameContainer.getWidth(), gameContainer.getHeight());
        terrain.render(graphics);

        renderPNJ(graphics);
        personnageAAfficher.render(graphics);

        interfaceJoueur.render(gameContainer, graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        interfaceJoueur.update(personnageAAfficher);
    }



    /**
     * Génére aléatoirement les PNJ depuis le serveurs, ainsi que les informations qui leurs sont relatives
     */
    private void initPNJ() {
        try {
            // RAZ de la liste des PNJ
            listePNJAAfficher = new ArrayList<>();

            ArrayList<PNJ> listeTmp;

            listeTmp = client.initialiseCombatPNJ();

            for (PNJ pnjTmp : listeTmp) {
                listePNJAAfficher.add(new PersonnageNonJoueur(pnjTmp));
            }

            for (int i = 0; i < listePNJAAfficher.size(); i++) {
                listePNJAAfficher.get(i).setEnCombat(true);
            }
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Génére le joueur et lui attribut une position aléatoire
     */
    private void initJoueur() {
        try {
            personnageAAfficher = new PersonnageJoueurClient(client.initialiseCombatJoueur());
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialise l'interface de combat
     */
    private void initInterface(GameContainer gameContainer) throws SlickException {
        interfaceJoueur = new InterfaceJoueur(gameContainer, this, personnageAAfficher.getCompetences());
    }

    private void renderPNJ(Graphics graphics) {
        for (PersonnageNonJoueur pnj : listePNJAAfficher) {
            pnj.render(graphics);
        }
    }

    public InterfaceJoueur getInterfaceJoueur() {
        return interfaceJoueur;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public PersonnageJoueurClient getPersonnageAAfficher() {
        return personnageAAfficher;
    }

    /**
     * Comunique et met a jour les informations avec le serveur
     */
    public void miseAJour() {
        try {
            Personnage personnageData = new Personnage(this.personnageAAfficher);
            ArrayList<PNJ> listeDataPNJ = new ArrayList<>();

            for (PersonnageNonJoueur personnageNonJoueur : listePNJAAfficher) {
                listeDataPNJ.add(new PNJ(personnageNonJoueur));
            }

            // Mise a jour des données sur le serveur puis remise a jours des attributs des paramettres
            client.updateServeurCombat(personnageData, listeDataPNJ);

            listePNJAAfficher = new ArrayList<>();

            for (PNJ pnj : listeDataPNJ) {
                listePNJAAfficher.add(new PersonnageNonJoueur(pnj));
            }

            for (int i = 0; i < listePNJAAfficher.size(); i++) {
                listePNJAAfficher.get(i).setEnCombat(true);
            }

            this.personnageAAfficher = new PersonnageJoueurClient(personnageData);

        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
    }

    public void fin() {
        if (personnageAAfficher.getStats().getVieRestante() == 0 || listePNJAAfficher.size() == 0) {
            Personnage personnageData = new Personnage(this.personnageAAfficher);
            ArrayList<PNJ> listeDataPNJ = new ArrayList<>();

            for (PersonnageNonJoueur personnageNonJoueur : listePNJAAfficher) {
                listeDataPNJ.add(new PNJ(personnageNonJoueur));
            }

            // Mise a jour des données sur le serveur puis remise a jours des attributs des paramettres
            client.finServeurCombat(personnageData, listeDataPNJ);

            stateBasedGame.enterState(MapGameState.ID);
        }
    }

    /**
     * Déplace le joueur sur la case dont l'ID est passé en paramettre, uniquement si l'ID est a sa porté,
     * en prennant en compte ses stats
     * @param id de la case a atteindre
     */
    public void deplacement(int id) {
        float x, y;

        // Verification que le joueur a bien clicker sur le terrain
        if (id > 0) {

            // Convertion des coordonner du joueur en coordonner de combats
            x = (personnageAAfficher.getPositionX() + 32) / TAILLE_CASE;
            y = (personnageAAfficher.getPositionY() + 16) / TAILLE_CASE;

            if (terrain.caseAPortee(id, 1, x, y) && 0 < personnageAAfficher.getStats().getMouvementsRestants()){
                // Déplacement du joueur sur la case ou le joueur a cliquer
                personnageAAfficher.setPositionX(terrain.getXById(id) * CombatGameState.TAILLE_CASE - 32);
                personnageAAfficher.setPositionY(terrain.getYById(id) * CombatGameState.TAILLE_CASE - 16);
                personnageAAfficher.getStats().setMouvementsRestants(personnageAAfficher.getStats().getMouvementsRestants() - 1);
            }
        }
    }

    public ArrayList<PersonnageNonJoueur> getListePNJAAfficher() {
        return listePNJAAfficher;
    }

    public void tourSuivant() {
        miseAJour();
    }

    /**
     * Appeller lorsqu'un clique est fait sur l'un des bouton compétence.
     * Verifie que le joueur peut utiliser la compétance passer en ID.
     * Soustrait le cout de la compétance au nombre d'action que peut réaliser le joueur
     * @param idCompetence id de la compétance choisis
     * @return La liste des cases ou le joueur peux cliquer
     */
    public ArrayList<Case> action(int idCompetence) {
        Competence competence;
        ArrayList<Case> aRetourner = new ArrayList<>();

        // On recherche à quelle compétence correspond l'ID
        for (int i = 0; i < personnageAAfficher.getCompetences().size(); i++) {
            if (personnageAAfficher.getCompetences().get(i).getId() == idCompetence) {
                competence = personnageAAfficher.getCompetences().get(i);
                // On verifie que l'utilisateur a assez de point d'action pour l'utiliser
                if (competence.getCout() <= personnageAAfficher.getStats().getActionsRestantes()) {
                    // Deduis le coup de l'utilisation de la compétance
                    personnageAAfficher.getStats().setActionsRestantes(
                            personnageAAfficher.getStats().getActionsRestantes() - competence.getCout()
                    );
                    // On l'utilise
                    aRetourner = affichageCaseAPorteeCompetence(competence);
                }
            }
        }

        return aRetourner;
    }

    /**
     * Appeller apres un clic sur les bouton compétance.
     * Affiche les case qui sont a porté pour utiliser les compétance.
     * @param competence compétance que l'on veux utiliser
     * @return une arrayliste composée des case sur lequelle le joueur peux cliquer
     */
    private ArrayList<Case> affichageCaseAPorteeCompetence(Competence competence) {

        ArrayList<Case> aRetourner = new ArrayList<>();

        // Convertion des coordonner du joueur en coordonner de combats
        float x = (personnageAAfficher.getPositionX() + 32) / TAILLE_CASE;
        float y = (personnageAAfficher.getPositionY() + 16) / TAILLE_CASE;

        // Colorier en rouge les case inateniable et en vers celle qui le sont
        // Parcour du total des cases du terrain
        for (int i = 1; i <= terrain.getNombreCase(); i++) {
            if (terrain.caseAPortee(i, competence.getPortee(), x, y)) {
                terrain.getCase(i).setColorVert(true);
                aRetourner.add(terrain.getCase(i));
            } else {
                terrain.getCase(i).setColorRouge(true);
            }
        }

        return aRetourner;
    }

    /**
     * @param idCase id de la case sur lequel on clique afin de pouvoir utilisé la compétence
     * @param idCompetance id de la compétence choisie
     */
    public void utilisisationCompetence(int idCase, int idCompetance) {

        Competence competence = new Competence();

        for (Competence competanceJoueur : personnageAAfficher.getCompetences()) {
            if (idCompetance == competanceJoueur.getId()) {
                competence = competanceJoueur;
            }
        }

        // RAZ de l'affichage des compétance
        for (int i = 1; i <= terrain.getNombreCase(); i++) {
            terrain.getCase(i).setNonColorer(true);
        }

        // Chercher si un PNJ existe sur la case
        Case caseSelect = terrain.getCase(idCase);
        for (int i = 0; i < listePNJAAfficher.size(); i++) {
            if (caseSelect.inCase((int) listePNJAAfficher.get(i).getPositionX(), (int) listePNJAAfficher.get(i).getPositionY())) {
                // => Il existe => On lui applique les dégats et les effets de la compétance
                listePNJAAfficher.get(i).getStats().setVieRestante(listePNJAAfficher.get(i).getStats().getVieRestante() - competence.getDegaBase());

                // Gestion effet
                listePNJAAfficher.get(i).setEffet(client.chargementEffet(competence.getIdEffet()));

                if (listePNJAAfficher.get(i).getStats().getVieRestante() <= 0) {
                    listePNJAAfficher.remove(i);
                }

            }
            // => Il existe pas => On ne fait rien
        }
    }

    /**
     * Permet de désafficher l'interface de selection d'une cible de compétance
     * et rembourse le joueur sur le coup de la compétence
     * @param idCompetence id de la compétance a rembourser
     */
    public void nonUtilisationCompetence(int idCompetence) {
        // RAZ de l'affichage des compétance
        for (int i = 1; i <= terrain.getNombreCase(); i++) {
            terrain.getCase(i).setNonColorer(true);
        }

        // RAZ du coup de la compétance
        for (Competence competanceJoueur : personnageAAfficher.getCompetences()) {
            if (idCompetence == competanceJoueur.getId()) {
                personnageAAfficher.getStats().setActionsRestantes(personnageAAfficher.getStats().getActionsRestantes() + competanceJoueur.getCout());
            }
        }
    }
}
