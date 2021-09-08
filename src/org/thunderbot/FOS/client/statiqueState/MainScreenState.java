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
    private Image background;
    private StateBasedGame game;

    private TextField zoneSaisiePseudo;
    private TextField zoneSaisieMotDePasse;

    private Client client;

    public MainScreenState(Client client) {
        this.client = client;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.game = stateBasedGame;
        background = new Image("res/menuState/background.png");

        //L'initialisation du TextField
        zoneSaisiePseudo     = new TextField(gameContainer, gameContainer.getDefaultFont(), 110, 30, 480, 40);
        zoneSaisieMotDePasse = new TextField(gameContainer, gameContainer.getDefaultFont(), 110, 60, 480, 40);

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        background.draw(0, 0, container.getWidth(), container.getHeight());
        g.drawString("Appuyer sur une touche", 300, 300);
        zoneSaisiePseudo.render(container, g);
        zoneSaisieMotDePasse.render(container, g);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void keyReleased(int key, char c) {
        if (key == Input.KEY_ENTER) {
            try {
                connexionJeu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Entrée dans la map game state et authentification au serveur multijoueur
     */
    private void connexionJeu() throws IOException {

        int code;
        JFrame jFrame = new JFrame();

        String pseudo = zoneSaisiePseudo.getText();
        String mdp = zoneSaisieMotDePasse.getText();
        code = client.authentification(pseudo, mdp);
        client.setPseudo(pseudo);

        switch (code) {
            case 0:
                game.enterState(MapGameState.ID);
                JOptionPane.showMessageDialog(jFrame,
                        "Connexion réussie ! ");
                break;
            case 1 :
                JOptionPane.showMessageDialog(jFrame,
                        "Ce pseudo est déjà utilisé !");
                break;
            case 2 :
                JOptionPane.showMessageDialog(jFrame,
                        "Pseudo ou mot de passe incorrect");
                break;
        }

    }
}
