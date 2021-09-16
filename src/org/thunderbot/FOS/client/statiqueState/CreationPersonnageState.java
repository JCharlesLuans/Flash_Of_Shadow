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

import java.util.ArrayList;

public class CreationPersonnageState extends BasicGameState {

    public static final int ID = 3;

    private static final int ZT_NOM_WIDTH = 480;
    private static final int ZT_NOM_HEIGHT = 40;
    private static final int ZT_NOM_DELTA_HEIGHT = 120;

    private static final int BTN_VALIDER_LONGUEUR = 100;
    private static final int BTN_VALIDER_HAUTEUR  = 50;
    private static final int BTN_VALIDER_DELTA = 65;

    private static final int BTN_CLASSE_X_START = 100;
    private static final int BTN_CLASSE_Y_START = 50;

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

    private Classe classeSelectionner;

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

        click = new Sound("res/menuState/son/click.wav");

        desc_archer = new ImageFlottante("res/menuState/creationJoueur/descriptionClasse/archer.png");
        desc_mage = new ImageFlottante("res/menuState/creationJoueur/descriptionClasse/mage.png");
        desc_pretre = new ImageFlottante("res/menuState/creationJoueur/descriptionClasse/pretre.png");
        desc_guerrier = new ImageFlottante("res/menuState/creationJoueur/descriptionClasse/guerrier.png");
        desc_voleur = new ImageFlottante("res/menuState/creationJoueur/descriptionClasse/voleur.png");
        desc_pugilat = new ImageFlottante("res/menuState/creationJoueur/descriptionClasse/pugilat.png");

        int x = BTN_CLASSE_X_START;

        btnImg_archer   = new BoutonImage("res/menuState/creationJoueur/boutonClasse/archer.png", x,  BTN_CLASSE_Y_START);
        x+= 128;
        btnImg_mage     = new BoutonImage("res/menuState/creationJoueur/boutonClasse/mage.png", x,  BTN_CLASSE_Y_START);
        x+= 128;
        btnImg_pretre   = new BoutonImage("res/menuState/creationJoueur/boutonClasse/pretre.png", x,  BTN_CLASSE_Y_START);
        x+= 128;
        btnImg_guerrier = new BoutonImage("res/menuState/creationJoueur/boutonClasse/guerrier.png", x,  BTN_CLASSE_Y_START);
        x+= 128;
        btnImg_voleur   = new BoutonImage("res/menuState/creationJoueur/boutonClasse/voleur.png", x,  BTN_CLASSE_Y_START);
        x+= 128;
        btnImg_pugilat  = new BoutonImage("res/menuState/creationJoueur/boutonClasse/pugilat.png", x,  BTN_CLASSE_Y_START);

        background = new Image("res/menuState/creationJoueur/backgroundCreationPerso.jpg");

        btnValider = new Bouton(gameContainer.getWidth() / 2 - BTN_VALIDER_LONGUEUR / 2,
                gameContainer.getHeight() - BTN_VALIDER_DELTA,
                BTN_VALIDER_LONGUEUR, BTN_VALIDER_HAUTEUR, "Valider");

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

    public void gestionBoutonClasse(int x, int y) {
        if (btnImg_archer.isInLayout(x, y)) {
            btnImg_archer.setSelectionner(true);
            btnImg_mage.setSelectionner(false);
            btnImg_pretre.setSelectionner(false);
            btnImg_guerrier.setSelectionner(false);
            btnImg_voleur.setSelectionner(false);
            btnImg_pugilat.setSelectionner(false);

            click.play();
            classeSelectionner = client.chargementListeClasse("Archer");
        } else  if (btnImg_mage.isInLayout(x, y)) {
            btnImg_archer.setSelectionner(false);
            btnImg_mage.setSelectionner(true);
            btnImg_pretre.setSelectionner(false);
            btnImg_guerrier.setSelectionner(false);
            btnImg_voleur.setSelectionner(false);
            btnImg_pugilat.setSelectionner(false);

            click.play();
            classeSelectionner = client.chargementListeClasse("Mage");
        } else  if (btnImg_pretre.isInLayout(x, y)) {
            btnImg_archer.setSelectionner(false);
            btnImg_mage.setSelectionner(false);
            btnImg_pretre.setSelectionner(true);
            btnImg_guerrier.setSelectionner(false);
            btnImg_voleur.setSelectionner(false);
            btnImg_pugilat.setSelectionner(false);

            click.play();
            classeSelectionner = client.chargementListeClasse("Pretre");
        } else  if (btnImg_guerrier.isInLayout(x, y)) {
            btnImg_archer.setSelectionner(false);
            btnImg_mage.setSelectionner(false);
            btnImg_pretre.setSelectionner(false);
            btnImg_guerrier.setSelectionner(true);
            btnImg_voleur.setSelectionner(false);
            btnImg_pugilat.setSelectionner(false);

            click.play();
            classeSelectionner = client.chargementListeClasse("Guerrier");
        } else  if (btnImg_voleur.isInLayout(x, y)) {
            btnImg_archer.setSelectionner(false);
            btnImg_mage.setSelectionner(false);
            btnImg_pretre.setSelectionner(false);
            btnImg_guerrier.setSelectionner(false);
            btnImg_voleur.setSelectionner(true);
            btnImg_pugilat.setSelectionner(false);

            click.play();
            classeSelectionner = client.chargementListeClasse("Voleur");
        } else  if (btnImg_pugilat.isInLayout(x, y)) {
            btnImg_archer.setSelectionner(false);
            btnImg_mage.setSelectionner(false);
            btnImg_pretre.setSelectionner(false);
            btnImg_guerrier.setSelectionner(false);
            btnImg_voleur.setSelectionner(false);
            btnImg_pugilat.setSelectionner(true);

            click.play();
            classeSelectionner = client.chargementListeClasse("Pugilat");
        } else {
            btnImg_archer.setSelectionner(false);
            btnImg_mage.setSelectionner(false);
            btnImg_pretre.setSelectionner(false);
            btnImg_guerrier.setSelectionner(false);
            btnImg_voleur.setSelectionner(false);
            btnImg_pugilat.setSelectionner(false);
        }
    }

}
