/*
 * CreationPersonnageState.java             15/09/2021
 * Copyright et copyleft TNLag Corp.
 */
package org.thunderbot.FOS.client.statiqueState;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.thunderbot.FOS.client.gameState.MapGameState;
import org.thunderbot.FOS.client.gameState.entite.Personnage;
import org.thunderbot.FOS.client.network.Client;
import org.thunderbot.FOS.client.statiqueState.layout.*;
import org.thunderbot.FOS.client.statiqueState.police.MedievalSharp;
import org.thunderbot.FOS.database.beans.Classe;
import org.thunderbot.FOS.database.beans.Faction;
import org.thunderbot.FOS.database.beans.Map;

public class CreationPersonnageState extends BasicGameState {

    public static final int ID = 3;

    private static final int START_TITRE_Y = 25;

    /** Position de la zone texte */
    private static final int ZT_NOM_WIDTH = 480;
    private static final int ZT_NOM_HEIGHT = 40;
    private static final int ZT_NOM_DELTA_HEIGHT = 120;

    /** Position du bouton valider */
    private static final int BTN_VALIDER_LONGUEUR = 100;
    private static final int BTN_VALIDER_HAUTEUR  = 50;
    private static final int BTN_VALIDER_DELTA = 65;

    /** Position des boutons classes */
    private static final int BTN_CLASSE_X_START = 100;
    private static final int BTN_CLASSE_Y_START = START_TITRE_Y + 128;

    /** Position des boutons faction */
    private static final int BTN_FACTION_X_START = 100;
    private static final int BTN_FACTION_Y_START = BTN_CLASSE_Y_START + 128;

    /**Position des boutons sprite */
    private static final int BTN_SPRITE_Y = BTN_FACTION_Y_START + 128;

    /** Espace entre les bouton sur l'axe X */
    private static final int DELTA_ESPACE_BOUTON = 64;

    private static final int ZT_CHAR_MAX = 15;
    private static final int ZT_CHAR_MIN = 3;

    private static final String ERR_CLASSE_NULL = "Veuillez selectionner \nune classe !";
    private static final String ERR_FACTION_NULL = "Veuillez selectionner \nune faction !";
    private static final String ERR_PSEUDO =
            "Veuillez saisir un \npseudo valide entre \n" + ZT_CHAR_MIN + " et " + ZT_CHAR_MAX +  " caractères !";

    private static final String INF_CONFIRMATION = "Etes-vous sûr de vouloir créer\n ce personnage ?";

    // Gestion du nombre de sprite
    private static final int NOMBRE_SPRITE_MAX = 4;


    private Client client;

    private boolean valide;

    private StateBasedGame stateBasedGame;

    private Image imgBackground;
    private Image imgTitre;
    private Image imgClasse;
    private Image imgFaction;
    private Image imgNom;

    private BoutonImage btnValider;
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

    private BoutonImage btnImg_droite;
    private BoutonImage btnImg_gauche;
    private Image cadreSprite;

    private ImageFlottante desc_idenia;
    private ImageFlottante desc_umbra;
    private ImageFlottante desc_ethernia;

    private Classe classeSelectionner;
    private Faction factionSelectionner;

    private Sound click;

    private FenetrePopUpChoix fenetreConfirmation;
    private FenetrePopUp fenetreErreur;

    private SpriteSheet spriteAfficher;
    private int numeroSprite;
    private Animation animation;

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

        MedievalSharp font = new MedievalSharp(55);

        valide = false;

        this.stateBasedGame = stateBasedGame;

        int x = gameContainer.getWidth() / 2;

        // Init fenetree popup
        fenetreConfirmation = new FenetrePopUpChoix("");
        fenetreErreur = new FenetrePopUp("");

        // Init du background
        imgBackground = new Image("res/menuState/creationJoueur/background.png");
        imgTitre = new Image("res/menuState/creationJoueur/titre.png");
        imgClasse = new Image("res/menuState/creationJoueur/sousTitre_classe.png");
        imgFaction = new Image("res/menuState/creationJoueur/sousTitre_faction.png");
        imgNom = new Image("res/menuState/creationJoueur/sousTitre_nom.png");

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
        btnImg_archer   = new BoutonImage("res/menuState/creationJoueur/boutonClasse/archer.png", 0,  BTN_CLASSE_Y_START);
        btnImg_mage     = new BoutonImage("res/menuState/creationJoueur/boutonClasse/mage.png", 0,  BTN_CLASSE_Y_START);
        btnImg_pretre   = new BoutonImage("res/menuState/creationJoueur/boutonClasse/pretre.png", 0,  BTN_CLASSE_Y_START);
        btnImg_guerrier = new BoutonImage("res/menuState/creationJoueur/boutonClasse/guerrier.png", 0,  BTN_CLASSE_Y_START);
        btnImg_voleur   = new BoutonImage("res/menuState/creationJoueur/boutonClasse/voleur.png", 0,  BTN_CLASSE_Y_START);
        btnImg_pugilat  = new BoutonImage("res/menuState/creationJoueur/boutonClasse/pugilat.png", 0,  BTN_CLASSE_Y_START);

        // Gestion de la position des bouton
        x -= 3 * btnImg_archer.getWidth() + 3 * DELTA_ESPACE_BOUTON;
        btnImg_archer.setX(x);
        x += btnImg_archer.getWidth() + DELTA_ESPACE_BOUTON;
        btnImg_mage.setX(x);
        x += btnImg_mage.getWidth() + DELTA_ESPACE_BOUTON;
        btnImg_pretre.setX(x);
        x += btnImg_pretre.getWidth() + DELTA_ESPACE_BOUTON;
        btnImg_guerrier.setX(x);
        x += btnImg_guerrier.getWidth() + DELTA_ESPACE_BOUTON;
        btnImg_voleur.setX(x);
        x += btnImg_voleur.getWidth() + DELTA_ESPACE_BOUTON;
        btnImg_pugilat.setX(x);

        // Init des images flottante des factions
        desc_idenia = new ImageFlottante("res/menuState/creationJoueur/descriptionFaction/idenia.png");
        desc_umbra = new ImageFlottante("res/menuState/creationJoueur/descriptionFaction/umbra.png");
        desc_ethernia = new ImageFlottante("res/menuState/creationJoueur/descriptionFaction/ethernia.png");

        // Init des bouton pour gerer les factions
        btnImg_idenia = new BoutonImage("res/menuState/creationJoueur/boutonFaction/idenia.png", 0, BTN_FACTION_Y_START);
        btnImg_umbra = new BoutonImage("res/menuState/creationJoueur/boutonFaction/umbra.png", 0, BTN_FACTION_Y_START);
        btnImg_ethernia = new BoutonImage("res/menuState/creationJoueur/boutonFaction/ethernia.png", 0, BTN_FACTION_Y_START);

        // Gestion de la position des bouton
        x = gameContainer.getWidth() / 2 - btnImg_umbra.getWidth() / 2;
        btnImg_umbra.setX(x);
        btnImg_idenia.setX((int) btnImg_umbra.getX() + btnImg_umbra.getWidth() + DELTA_ESPACE_BOUTON);
        btnImg_ethernia.setX((int) btnImg_umbra.getX() - btnImg_umbra.getWidth() - DELTA_ESPACE_BOUTON);

        // Init des bouton pour le sprite
        cadreSprite = new Image("res/menuState/creationJoueur/cadreSprite.png");
        x = gameContainer.getWidth() / 2 - cadreSprite.getWidth() / 2; // Centre de l'image
        btnImg_droite = new BoutonImage("res/menuState/creationJoueur/droite.png", x + DELTA_ESPACE_BOUTON + cadreSprite.getWidth(), BTN_SPRITE_Y + cadreSprite.getHeight() / 8);
        btnImg_gauche = new BoutonImage("res/menuState/creationJoueur/gauche.png", x - DELTA_ESPACE_BOUTON - cadreSprite.getWidth() / 2, BTN_SPRITE_Y + cadreSprite.getHeight() / 8);

        // Init du bouton valider
        btnValider = new BoutonImage("res/menuState/creationJoueur/valider.png", gameContainer.getWidth() / 2 - BTN_VALIDER_LONGUEUR / 2,
                gameContainer.getHeight() - BTN_VALIDER_DELTA);

        // Init de la zone texte
        ztNom = new TextField(gameContainer, gameContainer.getDefaultFont(),
                gameContainer.getWidth() / 2 - ZT_NOM_WIDTH / 2,
                gameContainer.getHeight() - ZT_NOM_DELTA_HEIGHT,
                ZT_NOM_WIDTH, ZT_NOM_HEIGHT);

        // Init du sprite
        numeroSprite = 1;
        gestionSprite();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

        imgBackground.draw(0, 0, gameContainer.getWidth(), gameContainer.getHeight());

        graphics.drawImage(imgTitre, gameContainer.getWidth() / 2 - imgTitre.getWidth() / 2, START_TITRE_Y);
        graphics.drawImage(imgClasse,  gameContainer.getWidth() / 2 - imgTitre.getWidth() / 2 - imgClasse.getWidth() , BTN_CLASSE_Y_START);
        graphics.drawImage(imgFaction, gameContainer.getWidth() / 2 - imgTitre.getWidth() / 2 - imgFaction.getWidth(), BTN_FACTION_Y_START);
        graphics.drawImage(imgNom, ztNom.getX() - imgNom.getWidth(), ztNom.getY());

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

        btnImg_droite.render(graphics);
        btnImg_gauche.render(graphics);
        graphics.drawImage(cadreSprite, gameContainer.getWidth() / 2 - cadreSprite.getWidth() / 2, BTN_SPRITE_Y);
        graphics.drawAnimation(animation, gameContainer.getWidth() / 2 - animation.getWidth() / 2, BTN_SPRITE_Y);

        // Affichage des description en dernier pour qu'lles s'affiche au dessus de tout
        desc_archer.render(graphics);
        desc_mage.render(graphics);
        desc_pretre.render(graphics);
        desc_guerrier.render(graphics);
        desc_voleur.render(graphics);
        desc_pugilat.render(graphics);

        desc_idenia.render(graphics);
        desc_umbra.render(graphics);
        desc_ethernia.render(graphics);

        fenetreConfirmation.render(graphics);
        fenetreErreur.render(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (valide && !fenetreConfirmation.isShow() && fenetreConfirmation.isOui()) {
            client.getPersonnage().setNom(ztNom.getText());
            client.getPersonnage().setClasse(classeSelectionner);
            client.getPersonnage().setFaction(factionSelectionner);
            client.getPersonnage().setMap(factionSelectionner.getMapStart());
            client.getPersonnage().setX(Map.POSITION_X_DEFAUT);
            client.getPersonnage().setY(Map.POSITION_Y_DEFAUT);
            client.getPersonnage().setSprite("sprite" + numeroSprite + ".png");
            client.chargementStuffBase();

            client.createPersonnage();
            stateBasedGame.enterState(MapGameState.ID);
        }
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        if (fenetreConfirmation.isShow()) {
            fenetreConfirmation.mouseClicked(button, x, y, clickCount);
        } else if (fenetreErreur.isShow()) {
            fenetreErreur.mouseClicked(button,x,y,clickCount);
        } else {
            gestionBoutonValider(x, y);
            gestionBoutonClasse(x, y);
            gestionBoutonFaction(x, y);
            gestionBoutonSprite(x, y);
        }
    }

    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        if (!fenetreConfirmation.isShow() && !fenetreErreur.isShow()) {
            gestionDescriptionClasse(newx, newy);
            gestionDescriptionFaction(newx, newy);
        }
    }

    /**
     * Gestion des actions lors d'un click sur les bouton des classe.
     * Va permettre de cocher un bouton si l'on click dessus, et de décocher tout les autres.
     * Si un bouton est cocher, alors classeSelectionner prend la valeur de la classe choisie
     * @param x position en X de la souris
     * @param y position en Y de la souris
     */
    private void gestionBoutonClasse(int x, int y) {
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

    private void gestionBoutonFaction(int x, int y) {
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

    private void gestionBoutonSprite(int x, int y) {
        if (btnImg_gauche.isInLayout(x, y)) {
            numeroSprite--;
            if (numeroSprite <= 0) {
                numeroSprite = NOMBRE_SPRITE_MAX;
            }
            gestionSprite();
            click.play();
        }

        if (btnImg_droite.isInLayout(x, y)) {
            numeroSprite++;
            if (numeroSprite > NOMBRE_SPRITE_MAX) {
                numeroSprite = 1;
            }
            gestionSprite();
            click.play();
        }
    }

    private void gestionSprite() {
        try {
            spriteAfficher = new SpriteSheet("res/texture/sprite/joueur/sprite" + numeroSprite + ".png", 64, 64);
            animation = Personnage.loadAnimation(spriteAfficher, 1, 9, 2);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    private void gestionDescriptionClasse(int x, int y) {
        desc_archer.setVisible(false);
        desc_mage.setVisible(false);
        desc_pretre.setVisible(false);
        desc_guerrier.setVisible(false);
        desc_voleur.setVisible(false);
        desc_pugilat.setVisible(false);

        if(btnImg_archer.isInLayout(x, y)) {
            desc_archer.setX(x);
            desc_archer.setY(y);
            desc_archer.setVisible(true);
        }

        if(btnImg_mage.isInLayout(x, y)) {
            desc_mage.setX(x);
            desc_mage.setY(y);
            desc_mage.setVisible(true);
        }

        if(btnImg_pretre.isInLayout(x, y)) {
            desc_pretre.setX(x);
            desc_pretre.setY(y);
            desc_pretre.setVisible(true);
        }

        if(btnImg_guerrier.isInLayout(x, y)) {
            desc_guerrier.setX(x);
            desc_guerrier.setY(y);
            desc_guerrier.setVisible(true);
        }

        if(btnImg_voleur.isInLayout(x, y)) {
            desc_voleur.setX(x);
            desc_voleur.setY(y);
            desc_voleur.setVisible(true);
        }

        if(btnImg_pugilat.isInLayout(x, y)) {
            desc_pugilat.setX(x);
            desc_pugilat.setY(y);
            desc_pugilat.setVisible(true);
        }
    }

    private void gestionDescriptionFaction(int x, int y) {
        desc_idenia.setVisible(false);
        desc_umbra.setVisible(false);
        desc_ethernia.setVisible(false);

        if (btnImg_idenia.isInLayout(x, y)) {
            desc_idenia.setX(x);
            desc_idenia.setY(y);
            desc_idenia.setVisible(true);
        }

        if (btnImg_umbra.isInLayout(x, y)) {
            desc_umbra.setX(x);
            desc_umbra.setY(y);
            desc_umbra.setVisible(true);
        }

        if (btnImg_ethernia.isInLayout(x, y)) {
            desc_ethernia.setX(x);
            desc_ethernia.setY(y);
            desc_ethernia.setVisible(true);
        }
    }

    private void gestionBoutonValider(int x, int y) {
        if (btnValider.isInLayout(x, y)) {

            click.play();

            // Gestion erreur
            if (classeSelectionner == null) {
                fenetreErreur.setMessage(ERR_CLASSE_NULL);
                fenetreErreur.setShow(true);
            } else if (factionSelectionner == null) {
                fenetreErreur.setMessage(ERR_FACTION_NULL);
                fenetreErreur.setShow(true);
            } else if (ZT_CHAR_MIN > ztNom.getText().length() ||  ztNom.getText().length() > ZT_CHAR_MAX ) {
                fenetreErreur.setMessage(ERR_PSEUDO);
                fenetreErreur.setShow(true);
            } else {
                String recap = INF_CONFIRMATION+ '\n' + '\n';
                recap += "Nom : " + ztNom.getText() + '\n';
                recap += "Classe : " + classeSelectionner.getNom() + '\n';
                recap += "Faction : " + factionSelectionner.getNom() + '\n';
                recap += "Sprite : " + numeroSprite;

                fenetreConfirmation.setMessage(recap);
                fenetreConfirmation.setShow(true);
                valide = true;
            }


        }
    }
}
