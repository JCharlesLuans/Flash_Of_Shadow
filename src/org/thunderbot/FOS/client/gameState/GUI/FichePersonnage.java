package org.thunderbot.FOS.client.gameState.GUI;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.thunderbot.FOS.client.network.Client;
import org.thunderbot.FOS.database.beans.Objet;

public class FichePersonnage extends FenetreEnJeu{

    private static final String TITRE = "Personnage";

    private static final int DELTA_TITRE = 10;

    private static final int X_FERMER = 375;
    private static final int Y_FERMER = 5;
    private static final int LONGUEUR_FERMER = 30;
    private static final int HAUTEUR_FERMER = 25;

    private static final int NOMBRE_EQUIPEMENT = 6;

    private static final int EMPLACEMENT_X = 46;
    private static final int EMPLACEMENT_Y = 70;
    private static final int EMPLACEMENT_HAUTEUR = 92;
    private static final int EMPLACEMENT_LONGUEUR = 92;

    private Objet[] equipement;
    private ObjetContainer[] listeEmplacement;

    public FichePersonnage(Gui gui, Client client, GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super(gui, client, gameContainer, stateBasedGame);

        // Init de l'image de fond
        imgFond = new Image("res/menuState/gui/personnage.png");

        // Initialise la position de la fenetre
        initPosition(gameContainer);

        equipement = new Objet[NOMBRE_EQUIPEMENT];
        listeEmplacement = new ObjetContainer[NOMBRE_EQUIPEMENT];




    }

    public void render(Graphics graphics) {
        graphics.setFont(font.getFont());
        if (active) {
            graphics.drawImage(imgFond, centreX - imgFond.getWidth() / 2, centreY - imgFond.getHeight() / 2);
            graphics.drawString(TITRE, centreX - graphics.getFont().getWidth(TITRE) / 2, centreY - imgFond.getHeight() / 2 + DELTA_TITRE);

            for (int i = 0; i < NOMBRE_EQUIPEMENT; i++) {
                listeEmplacement[i].render(graphics);
            }

        }
    }

    public void setCentreX(int centreX) {
        this.centreX = centreX;
    }

    public void setCentreY(int centreY) {
        this.centreY = centreY;
    }

    @Override
    public void mouseMouved(int y, int x) {
        if (active) {

            for (int i = 0; i < NOMBRE_EQUIPEMENT; i++) {
                listeEmplacement[i].mouseMouved(y, x);
            }
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        x -= this.x;
        y -= this.y;
        if (active)
            fermer(x, y, X_FERMER, Y_FERMER, LONGUEUR_FERMER, HAUTEUR_FERMER);
    }

    @Override
    public void ouvrir() {
        super.ouvrir();
        try {
            initEquipementContainer();
        } catch (SlickException e) {
            e.printStackTrace();
        }


        // Chargement du stuff du personnage
        equipement[0] = client.getPersonnage().getStuffTete();
        equipement[1] = client.getPersonnage().getStuffTorse();
        equipement[2]= client.getPersonnage().getStuffGant();
        equipement[3] = client.getPersonnage().getStuffJambe();
        equipement[4] = client.getPersonnage().getStuffBotte();
        equipement[5] = client.getPersonnage().getStuffArme();

        for (int i = 0; i < NOMBRE_EQUIPEMENT; i++) {
            listeEmplacement[i].setObjet(equipement[i]);
        }
    }

    private void initEquipementContainer() throws SlickException {

        int centreX = this.centreX -= imgFond.getWidth() / 2;
        int centreY = this.centreY -= imgFond.getHeight() / 2;

        // variable pour le calcul des placement de ObjetContainer
        int xEmplacement = 0;
        int yEmplacement = 0;
        int nbColone = 0;
        int nbLigne = 0;
        int delta = 0;

        for (int i = 0; i < listeEmplacement.length; i++) {

            if (centreY + EMPLACEMENT_Y + (nbLigne + 1) * EMPLACEMENT_HAUTEUR > centreY + imgFond.getHeight() ) {
                nbColone++;
                nbLigne = 0;
            }

            if (nbColone > 0) {
                delta = EMPLACEMENT_X;
            }

            xEmplacement = centreX + EMPLACEMENT_X + delta + (nbColone) * EMPLACEMENT_LONGUEUR;
            yEmplacement = centreY + EMPLACEMENT_Y + (nbLigne * EMPLACEMENT_HAUTEUR);

            listeEmplacement[i] = new ObjetContainer(xEmplacement, yEmplacement, EMPLACEMENT_LONGUEUR, EMPLACEMENT_HAUTEUR);
            nbLigne++;
        }
    }
}
