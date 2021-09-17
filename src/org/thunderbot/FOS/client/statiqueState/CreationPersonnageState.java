/*
 * CreationPersonnageState.java             15/09/2021
 * Copyright et copyleft TNLag Corp.
 */
package org.thunderbot.FOS.client.statiqueState;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.thunderbot.FOS.client.network.Client;
import org.thunderbot.FOS.client.statiqueState.layout.Bouton;
import org.thunderbot.FOS.client.statiqueState.layout.BoutonImage;
import org.thunderbot.FOS.client.statiqueState.layout.ImageFlottante;
import org.thunderbot.FOS.database.beans.Classe;
import org.thunderbot.FOS.database.beans.Faction;

public class CreationPersonnageState extends BasicGameState {

    public static final int ID = 3;

    /** Position de la zone texte */
    private static final int ZT_NOM_WIDTH = 480;
    private static final int ZT_NOM_HEIGHT = 40;
    private static final int ZT_NOM_DELTA_HEIGHT = 120;

    /** Position du bouton valider */
    private static final int BTN_VALIDER_LONGUEUR = 100;
    private static final int BTN_VALIDER_HAUTEUR  = 50;
    private static final int BTN_VALIDER_DELTA = 65;

    /** Position des bouton classes */
    private static final int BTN_CLASSE_X_START = 100;
    private static final int BTN_CLASSE_Y_START = 50;

    /** Position des bouton faction */
    private static final int BTN_FACTION_X_START = 100;
    private static final int BTN_FACTION_Y_START = 178;

    /** Espace entre les bouton sur l'axe X */
    private static final int DELTA_ESPACE_BOUTON = 64;

    private Client client;

    private Image background;

    private Bouton btnValider;
    private TextField ztNom;

    private BoutonImage btnImg_archer;
    private BoutonImage btnImg_mage;
    private BoutonImage btnImg_pretre;
    private BoutonImage btnImg_guerrier;
    private BoutonImage btnImg_voleur;
    private BoutonImage btnImg_pugilat;

    private ImageFlottante desc_archer;
    private ImageFlottante desc_mage;
    private ImageFlottante desc_pretre;
    private ImageFlottante desc_guerrier;
    private ImageFlottante desc_voleur;
    private ImageFlottante desc_pugilat;

    private BoutonImage btnImg_idenia;
    private BoutonImage btnImg_umbra;
    private BoutonImage btnImg_ethernia;

    private Classe classeSelectionner;
    private Faction factionSelectionner;

    private Sound click;

    public CreationPersonnageState(Client client) {
        this.client = client;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

        int x;

        // Init du son
        click = new Sound("res/menuState/son/click.wav");

        // Init des image flottante des classes
        desc_archer = new ImageFlottante("res/menuState/creationJoueur/descriptionClasse/archer.png");
        desc_mage = new ImageFlottante("res/menuState/creationJoueur/descriptionClasse/mage.png");
        desc_pretre = new ImageFlottante("res/menuState/creationJoueur/descriptionClasse/pretre.png");
        desc_guerrier = new ImageFlottante("res/menuState/creationJoueur/descriptionClasse/guerrier.png");
        desc_voleur = new ImageFlottante("res/menuState/creationJoueur/descriptionClasse/voleur.png");
        desc_pugilat = new ImageFlottante("res/menuState/creationJoueur/descriptionClasse/pugilat.png");

        // Init des bouton pour gerer les classes
        x = BTN_CLASSE_X_START;
        btnImg_archer   = new BoutonImage("res/menuState/creationJoueur/boutonClasse/archer.png", x,  BTN_CLASSE_Y_START);
        x+= btnImg_archer.getWidth() + DELTA_ESPACE_BOUTON;
        btnImg_mage     = new BoutonImage("res/menuState/creationJoueur/boutonClasse/mage.png", x,  BTN_CLASSE_Y_START);
        x+= btnImg_mage.getWidth() + DELTA_ESPACE_BOUTON;
        btnImg_pretre   = new BoutonImage("res/menuState/creationJoueur/boutonClasse/pretre.png", x,  BTN_CLASSE_Y_START);
        x+= btnImg_pretre.getWidth() + DELTA_ESPACE_BOUTON;
        btnImg_guerrier = new BoutonImage("res/menuState/creationJoueur/boutonClasse/guerrier.png", x,  BTN_CLASSE_Y_START);
        x+= btnImg_guerrier.getWidth() + DELTA_ESPACE_BOUTON;
        btnImg_voleur   = new BoutonImage("res/menuState/creationJoueur/boutonClasse/voleur.png", x,  BTN_CLASSE_Y_START);
        x+= btnImg_voleur.getWidth() + DELTA_ESPACE_BOUTON;
        btnImg_pugilat  = new BoutonImage("res/menuState/creationJoueur/boutonClasse/pugilat.png", x,  BTN_CLASSE_Y_START);

        // Init des images flottante des factions
        // Init des bouton pour gerer les factions
        x = BTN_FACTION_X_START;
        btnImg_idenia = new BoutonImage("res/menuState/creationJoueur/boutonFaction/idenia.png", x, BTN_FACTION_Y_START);
        x += btnImg_idenia.getWidth() + DELTA_ESPACE_BOUTON;
        btnImg_umbra = new BoutonImage("res/menuState/creationJoueur/boutonFaction/umbra.png", x, BTN_FACTION_Y_START);
        x += btnImg_umbra.getWidth() + DELTA_ESPACE_BOUTON;
        btnImg_ethernia = new BoutonImage("res/menuState/creationJoueur/boutonFaction/ethernia.png", x, BTN_FACTION_Y_START);


        // Init du background
        background = new Image("res/menuState/creationJoueur/backgroundCreationPerso.jpg");

        // Init du bouton valider
        btnValider = new Bouton(gameContainer.getWidth() / 2 - BTN_VALIDER_LONGUEUR / 2,
                gameContainer.getHeight() - BTN_VALIDER_DELTA,
                BTN_VALIDER_LONGUEUR, BTN_VALIDER_HAUTEUR, "Valider");

        // Init de la zone texte
        ztNom = new TextField(gameContainer, gameContainer.getDefaultFont(),
                gameContainer.getWidth() / 2 - ZT_NOM_WIDTH / 2,
                gameContainer.getHeight() - ZT_NOM_DELTA_HEIGHT,
                ZT_NOM_WIDTH, ZT_NOM_HEIGHT);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        background.draw(0, 0, gameContainer.getWidth(), gameContainer.getHeight());

        btnValider.render(graphics);

        ztNom.render(gameContainer, graphics);

        btnImg_archer.render(graphics);
        btnImg_mage.render(graphics);
        btnImg_pretre.render(graphics);
        btnImg_guerrier.render(graphics);
        btnImg_voleur.render(graphics);
        btnImg_pugilat.render(graphics);

        btnImg_idenia.render(graphics);
        btnImg_umbra.render(graphics);
        btnImg_ethernia.render(graphics);

        desc_archer.render(graphics);
        desc_mage.render(graphics);
        desc_pretre.render(graphics);
        desc_guerrier.render(graphics);
        desc_voleur.render(graphics);
        desc_pugilat.render(graphics);


    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {

        if (btnValider.isInBouton(x, y)) {
            click.play();
        }

        gestionBoutonClasse(x, y);
        gestionBoutonFaction(x, y);
    }

    public void mouseMoved(int oldx, int oldy, int newx, int newy) {

        desc_archer.setVisible(false);
        desc_mage.setVisible(false);
        desc_pretre.setVisible(false);
        desc_guerrier.setVisible(false);
        desc_voleur.setVisible(false);
        desc_pugilat.setVisible(false);

        if(btnImg_archer.isInLayout(newx, newy)) {
            desc_archer.setX(newx);
            desc_archer.setY(newy);
            desc_archer.setVisible(true);
        }

        if(btnImg_mage.isInLayout(newx, newy)) {
            desc_mage.setX(newx);
            desc_mage.setY(newy);
            desc_mage.setVisible(true);
        }

        if(btnImg_pretre.isInLayout(newx, newy)) {
            desc_pretre.setX(newx);
            desc_pretre.setY(newy);
            desc_pretre.setVisible(true);
        }

        if(btnImg_guerrier.isInLayout(newx, newy)) {
            desc_guerrier.setX(newx);
            desc_guerrier.setY(newy);
            desc_guerrier.setVisible(true);
        }

        if(btnImg_voleur.isInLayout(newx, newy)) {
            desc_voleur.setX(newx);
            desc_voleur.setY(newy);
            desc_voleur.setVisible(true);
        }

        if(btnImg_pugilat.isInLayout(newx, newy)) {
            desc_pugilat.setX(newx);
            desc_pugilat.setY(newy);
            desc_pugilat.setVisible(true);
        }
    }

    /**
     * Gestion des actions lors d'un click sur les bouton des classe.
     * Va permettre de cocher un bouton si l'on lick dessus, et de décocher tout les autre.
     * @param x position en X de la souris
     * @param y position en Y de la souris
     */
    public void gestionBoutonClasse(int x, int y) {
        if (btnImg_archer.isInLayout(x, y)) {
            btnImg_archer.setSelectionner(true);
            btnImg_mage.setSelectionner(false);
            btnImg_pretre.setSelectionner(false);
            btnImg_guerrier.setSelectionner(false);
            btnImg_voleur.setSelectionner(false);
            btnImg_pugilat.setSelectionner(false);

            click.play();
            classeSelectionner = client.chargementClasse("Archer");
        } else  if (btnImg_mage.isInLayout(x, y)) {
            btnImg_archer.setSelectionner(false);
            btnImg_mage.setSelectionner(true);
            btnImg_pretre.setSelectionner(false);
            btnImg_guerrier.setSelectionner(false);
            btnImg_voleur.setSelectionner(false);
            btnImg_pugilat.setSelectionner(false);

            click.play();
            classeSelectionner = client.chargementClasse("Mage");
        } else  if (btnImg_pretre.isInLayout(x, y)) {
            btnImg_archer.setSelectionner(false);
            btnImg_mage.setSelectionner(false);
            btnImg_pretre.setSelectionner(true);
            btnImg_guerrier.setSelectionner(false);
            btnImg_voleur.setSelectionner(false);
            btnImg_pugilat.setSelectionner(false);

            click.play();
            classeSelectionner = client.chargementClasse("Pretre");
        } else  if (btnImg_guerrier.isInLayout(x, y)) {
            btnImg_archer.setSelectionner(false);
            btnImg_mage.setSelectionner(false);
            btnImg_pretre.setSelectionner(false);
            btnImg_guerrier.setSelectionner(true);
            btnImg_voleur.setSelectionner(false);
            btnImg_pugilat.setSelectionner(false);

            click.play();
            classeSelectionner = client.chargementClasse("Guerrier");
        } else  if (btnImg_voleur.isInLayout(x, y)) {
            btnImg_archer.setSelectionner(false);
            btnImg_mage.setSelectionner(false);
            btnImg_pretre.setSelectionner(false);
            btnImg_guerrier.setSelectionner(false);
            btnImg_voleur.setSelectionner(true);
            btnImg_pugilat.setSelectionner(false);

            click.play();
            classeSelectionner = client.chargementClasse("Voleur");
        } else  if (btnImg_pugilat.isInLayout(x, y)) {
            btnImg_archer.setSelectionner(false);
            btnImg_mage.setSelectionner(false);
            btnImg_pretre.setSelectionner(false);
            btnImg_guerrier.setSelectionner(false);
            btnImg_voleur.setSelectionner(false);
            btnImg_pugilat.setSelectionner(true);

            click.play();
            classeSelectionner = client.chargementClasse("Pugilat");
        }
    }

    public void gestionBoutonFaction(int x, int y) {
        if (btnImg_idenia.isInLayout(x, y)) {
            btnImg_idenia.setSelectionner(true);
            btnImg_ethernia.setSelectionner(false);
            btnImg_umbra.setSelectionner(false);

            click.play();
            factionSelectionner = client.chargementFaction("Idenia");
        } else if (btnImg_umbra.isInLayout(x, y)) {
            btnImg_idenia.setSelectionner(false);
            btnImg_ethernia.setSelectionner(false);
            btnImg_umbra.setSelectionner(true);

            click.play();
            factionSelectionner = client.chargementFaction("Umbra");
        } else if (btnImg_ethernia.isInLayout(x, y)) {
            btnImg_idenia.setSelectionner(false);
            btnImg_ethernia.setSelectionner(true);
            btnImg_umbra.setSelectionner(false);

            click.play();
            factionSelectionner = client.chargementFaction("Ethernia");
        }
    }
}
