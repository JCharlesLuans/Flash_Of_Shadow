/*
 * FlashOfShadow.java             24/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package org.thunderbot.FOS.client;

import org.lwjgl.Sys;
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
public class FlashOfShadow extends StateBasedGame {

    private Client client;

    /**
     * Lancement du jeu
     */
    public static void main(String[] args) throws SlickException, IOException {
        new AppGameContainer(new FlashOfShadow(), 1240, 720, false).start();
    }

    public FlashOfShadow() throws IOException {
        super("Flash Of Shadow");
        try {
            client = new Client();
        } catch (ConnectException e) {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Erreur lors de la connection au serveur ! Verifier votre connection, puis relancer le jeu");
            System.exit(1);
        }
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        addState(new MainScreenState(client));
        addState(new MapGameState(client));
        addState(new CreationPersonnageState(client));
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
