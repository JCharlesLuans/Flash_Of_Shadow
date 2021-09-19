/*
 * MainScreenState.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client.statiqueState;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.thunderbot.FOS.client.network.Client;
import org.thunderbot.FOS.client.gameState.MapGameState;
import org.newdawn.slick.gui.TextField;
import org.thunderbot.FOS.client.statiqueState.layout.BoutonImage;

import javax.swing.*;
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

    private static final int ZONE_SAISIE_LONGUEUR = 480;
    private static final int ZONE_SAISIE_HAUTEUR = 40;
    private static final int BOUTON_DELDA_SEPARATION = 75;

    private Image imgBackground;
    private Image imgTitre;
    private StateBasedGame stateBasedGame;

    private Sound sonClick;
    private Music musicBackground;

    private TextField zoneSaisiePseudo;
    private TextField zoneSaisieMotDePasse;

    private Client client;

    private BoutonImage boutonInscription;
    private BoutonImage boutonConnexion;

    public MainScreenState(Client client) {
        this.client = client;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

        int middleX = gameContainer.getWidth() / 2;
        int middleY = gameContainer.getHeight() / 2;

        musicBackground = new Music("res/menuState/son/Karstenholymoly-The_Thunderstorm.wav");

        this.stateBasedGame = stateBasedGame;

        imgBackground = new Image("res/menuState/menuPrincipal/background.png");
        imgTitre = new Image("res/menuState/menuPrincipal/titre.png");

        sonClick = new Sound("res/menuState/son/click.wav");

        boutonInscription = new BoutonImage("res/menuState/menuPrincipal/inscription.png", 680, middleY + ZONE_SAISIE_HAUTEUR * 2 + BOUTON_DELDA_SEPARATION);
        boutonConnexion = new BoutonImage("res/menuState/menuPrincipal/connexion.png", 680, middleY + ZONE_SAISIE_HAUTEUR * 2 + BOUTON_DELDA_SEPARATION);

        //L'initialisation du TextField
        zoneSaisiePseudo     = new TextField(gameContainer, gameContainer.getDefaultFont(), middleX - ZONE_SAISIE_LONGUEUR / 2, middleY, ZONE_SAISIE_LONGUEUR, ZONE_SAISIE_HAUTEUR);
        zoneSaisieMotDePasse = new TextField(gameContainer, gameContainer.getDefaultFont(), middleX - ZONE_SAISIE_LONGUEUR / 2, middleY + ZONE_SAISIE_HAUTEUR * 2, ZONE_SAISIE_LONGUEUR, ZONE_SAISIE_HAUTEUR);

        boutonInscription.setX(middleX - boutonInscription.getWidth() -  BOUTON_DELDA_SEPARATION);
        boutonConnexion.setX(middleX + BOUTON_DELDA_SEPARATION);

    }

    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        musicBackground.play();
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        imgBackground.draw(0, 0, container.getWidth(), container.getHeight());
        g.drawImage(imgTitre, container.getWidth() / 2 - imgTitre.getWidth() / 2, 15);

        zoneSaisiePseudo.render(container, g);
        zoneSaisieMotDePasse.render(container, g);

        boutonInscription.render(g);
        boutonConnexion.render(g);
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        musicBackground.stop();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        try {
            if (boutonConnexion.isInLayout(x, y)) {
                sonClick.play();
                boutonConnexion.setSelectionner(true);
                entreeJeu(CONNEXION);
            } else if (boutonInscription.isInLayout(x, y)) {
                sonClick.play();
                boutonInscription.setSelectionner(true);
                entreeJeu(INSCRIPTION);
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
        JFrame jFrame = new JFrame();

        String pseudo = zoneSaisiePseudo.getText();
        String mdp = zoneSaisieMotDePasse.getText();
        code = client.authentification(pseudo, mdp, nouveauJoueur);

        switch (code) {
            case 0:
                JOptionPane.showMessageDialog(jFrame,
                        "Connexion réussie ! ");

                if(nouveauJoueur) {
                    stateBasedGame.enterState(CreationPersonnageState.ID);
                } else {
                    stateBasedGame.enterState(MapGameState.ID);
                }


                break;
            case 1 :
                JOptionPane.showMessageDialog(jFrame,
                        "Ce pseudo est déjà utilisé !");
                boutonConnexion.setSelectionner(false);
                boutonInscription.setSelectionner(false);
                break;
            case 2 :
                JOptionPane.showMessageDialog(jFrame,
                        "Pseudo ou mot de passe incorrect");
                boutonConnexion.setSelectionner(false);
                boutonInscription.setSelectionner(false);
                break;

        }
    }
}
