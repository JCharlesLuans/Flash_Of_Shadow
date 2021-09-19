/*
 * FlashOfShadow.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.thunderbot.FOS.client.gameState.MapGameState;
import org.thunderbot.FOS.client.network.Client;
import org.thunderbot.FOS.client.statiqueState.CreationPersonnageState;
import org.thunderbot.FOS.client.statiqueState.MainScreenState;

import javax.swing.*;
import java.io.IOException;
import java.net.ConnectException;

/**
 * Jeu
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class ChaosRevolt extends StateBasedGame {

    public static final int WIDTH   = 1280;
    public static final int HEIGHT  = 720;

    private static final String TITRE = "Chaos Revolt";
    private static final String ERR_CONNEXION = "Erreur lors de la connection au serveur ! Verifier votre connection, puis relancer le jeu";

    private Client client;

    /**
     * Lancement du jeu
     */
    public static void main(String[] args) throws SlickException, IOException {
        AppGameContainer appGameContainer =  new AppGameContainer(new ChaosRevolt());
        appGameContainer.setDisplayMode(WIDTH, HEIGHT, false);
        appGameContainer.setShowFPS(false);
        appGameContainer.start();
    }

    public ChaosRevolt() throws IOException {
        super(TITRE);
        try {
            client = new Client();
        } catch (ConnectException e) {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, ERR_CONNEXION);
            System.exit(1);
        }
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new MainScreenState(client));
        this.addState(new CreationPersonnageState(client));
        this.addState(new MapGameState(client));
    }

    @Override
    public boolean closeRequested() {
        try {
            client.deconnexion();
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Déconnexion avec succés ! A bientot !");
        } catch (IOException e) {
           e.printStackTrace();
        }
        System.exit(0); // Use this if you want to quit the app.
        return false;
    }
}
