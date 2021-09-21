/*
 * MainScreenState.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.statiqueState;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.thunderbot.FOS.client.network.Client;
import org.thunderbot.FOS.client.gameState.MapGameState;
import org.newdawn.slick.gui.TextField;
import org.thunderbot.FOS.client.statiqueState.layout.BoutonImage;
import org.thunderbot.FOS.client.statiqueState.layout.FenetrePopUp;

import java.io.IOException;

/**
 * Menu principal
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class MainScreenState extends BasicGameState {

    public static final int ID = 1;

    public static final boolean INSCRIPTION = true;
    public static final boolean CONNEXION = false;

    private static final String ERR_SERVEUR = "Erreur lors de la \nconnection au serveur \n! Verifier votre \nconnexion, puis \nrelancer le jeu";
    private static final String ERR_PSEUDO_UTILISER =  "Ce pseudo est déjà \nutilisé !";
    private static final String ERR_PSEUDO_INEXISTANT =  "Pseudo ou mot de \npasse incorrect";
    private static final String INF_CONNEXION_OK =  "Connexion réussie ! ";

    private static final int ZONE_SAISIE_LONGUEUR = 480;
    private static final int ZONE_SAISIE_HAUTEUR = 40;
    private static final int BOUTON_DELDA_SEPARATION = 75;

    private Image imgBackground;
    private Image imgTitre;
    private Image imgSousTitreCompte;
    private Image imgSousTitreMDP;
    private StateBasedGame stateBasedGame;

    private Sound sonClick;
    private Music musicBackground;

    private TextField zoneSaisiePseudo;
    private TextField zoneSaisieMotDePasse;

    private Client client;

    private BoutonImage boutonInscription;
    private BoutonImage boutonConnexion;
    private BoutonImage boutonQuitter;

    private FenetrePopUp fenetrePopUp;

    private boolean connexion;
    private boolean connexionServeur;
    private boolean quitter;

    private boolean nouveauJoueur;

    public MainScreenState(Client client) {
        this.client = client;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

        fenetrePopUp = new FenetrePopUp(gameContainer, "", 55);

        gameContainer.setMouseCursor("res/menuState/gui/cursor.png", 0, 0);

        int middleX = gameContainer.getWidth() / 2;
        int middleY = gameContainer.getHeight() / 2;

        musicBackground = new Music("res/menuState/son/Karstenholymoly-The_Thunderstorm.wav");

        this.stateBasedGame = stateBasedGame;

        imgBackground = new Image("res/menuState/menuPrincipal/background.png");
        imgTitre = new Image("res/menuState/menuPrincipal/titre.png");
        imgSousTitreCompte = new Image("res/menuState/menuPrincipal/sousTitre_compte.png");
        imgSousTitreMDP = new Image("res/menuState/menuPrincipal/sousTitre_motDePasse.png");

        sonClick = new Sound("res/menuState/son/click.wav");

        boutonInscription = new BoutonImage("res/menuState/menuPrincipal/inscription.png", 680, middleY + ZONE_SAISIE_HAUTEUR * 2 + BOUTON_DELDA_SEPARATION);
        boutonConnexion = new BoutonImage("res/menuState/menuPrincipal/connexion.png", 680, middleY + ZONE_SAISIE_HAUTEUR * 2 + BOUTON_DELDA_SEPARATION);
        boutonQuitter = new BoutonImage("res/menuState/menuPrincipal/quitter.png", 0, boutonConnexion.getY() + BOUTON_DELDA_SEPARATION);



        //L'initialisation du TextField
        zoneSaisiePseudo     = new TextField(gameContainer, gameContainer.getDefaultFont(), middleX - ZONE_SAISIE_LONGUEUR / 2, middleY, ZONE_SAISIE_LONGUEUR, ZONE_SAISIE_HAUTEUR);
        zoneSaisieMotDePasse = new TextField(gameContainer, gameContainer.getDefaultFont(), middleX - ZONE_SAISIE_LONGUEUR / 2, middleY + ZONE_SAISIE_HAUTEUR * 2, ZONE_SAISIE_LONGUEUR, ZONE_SAISIE_HAUTEUR);

        boutonInscription.setX(middleX - boutonInscription.getWidth() -  BOUTON_DELDA_SEPARATION);
        boutonConnexion.setX(middleX + BOUTON_DELDA_SEPARATION);
        boutonQuitter.setX(middleX - boutonQuitter.getWidth() / 2);

    }

    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) {

        // Lancement de la musique
        musicBackground.play();

        // Connexion au serveur
        try {
            client.connectionServeur();
            connexionServeur = true;
        } catch (IOException e) {
            fenetrePopUp.setMessage(ERR_SERVEUR);
            fenetrePopUp.setShow(true);
            connexionServeur = false;
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

        imgBackground.draw(0, 0, container.getWidth(), container.getHeight());
        g.drawImage(imgTitre, container.getWidth() / 2 - imgTitre.getWidth() / 2, 15);
        g.drawImage(imgSousTitreCompte, zoneSaisiePseudo.getX() - imgSousTitreCompte.getWidth(), zoneSaisiePseudo.getY());
        g.drawImage(imgSousTitreMDP, zoneSaisieMotDePasse.getX() - imgSousTitreMDP.getWidth(), zoneSaisieMotDePasse.getY());

        zoneSaisiePseudo.render(container, g);
        zoneSaisieMotDePasse.render(container, g);

        boutonInscription.render(g);
        boutonConnexion.render(g);
        boutonQuitter.render(g);

        fenetrePopUp.render(g);
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        musicBackground.stop();
        connexion = false;
        fenetrePopUp.setShow(false);
        boutonInscription.setSelectionner(false);
        boutonConnexion.setSelectionner(false);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

        // verifie que l'on a bien fait l'étape d'authantification, puis que l'on ferme la fenetre pop up.
        if (connexion && !fenetrePopUp.isShow()) {
            if (nouveauJoueur) {
                stateBasedGame.enterState(CreationPersonnageState.ID);
            } else {
                stateBasedGame.enterState(MapGameState.ID);
            }
        } else if (quitter && !fenetrePopUp.isShow()) {
            System.exit(0);
        }
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {

        // Gestion de la fenetre PopUp qui souvre pour afficheer des information
        if (fenetrePopUp.isShow()) {
            fenetrePopUp.mouseClicked(button, x, y, clickCount);
        }

        try {
            if (boutonConnexion.isInLayout(x, y)) {
                sonClick.play();
                boutonConnexion.setSelectionner(true);
                entreeJeu(CONNEXION);
            } else if (boutonInscription.isInLayout(x, y)) {
                sonClick.play();
                boutonInscription.setSelectionner(true);
                entreeJeu(INSCRIPTION);
            } else if (boutonQuitter.isInLayout(x, y)) {
                sonClick.play();
                boutonQuitter.setSelectionner(true);
                fenetrePopUp.setMessage("A bientôt");
                fenetrePopUp.setShow(true);
                quitter = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Entrée dans la map game state et authentification au serveur multijoueur
     */
    private void entreeJeu(boolean nouveauJoueur) throws IOException {

        int code;

        String pseudo = zoneSaisiePseudo.getText();
        String mdp = zoneSaisieMotDePasse.getText();
        code = client.authentification(pseudo, mdp, nouveauJoueur);

        switch (code) {
            case 0:
                fenetrePopUp.setMessage(INF_CONNEXION_OK);
                fenetrePopUp.setShow(true);
                connexion = true;
                this.nouveauJoueur = nouveauJoueur;
                break;

            case 1 :
                fenetrePopUp.setMessage(ERR_PSEUDO_UTILISER);
                fenetrePopUp.setShow(true);
                boutonConnexion.setSelectionner(false);
                boutonInscription.setSelectionner(false);
                break;
            case 2 :
                fenetrePopUp.setMessage(ERR_PSEUDO_INEXISTANT);
                fenetrePopUp.setShow(true);
                boutonConnexion.setSelectionner(false);
                boutonInscription.setSelectionner(false);
                break;

        }
    }

}
