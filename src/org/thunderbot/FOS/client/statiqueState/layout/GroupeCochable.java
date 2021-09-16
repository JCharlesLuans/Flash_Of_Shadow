package org.thunderbot.FOS.client.statiqueState.layout;


import org.newdawn.slick.Graphics;

import java.util.ArrayList;

public class GroupeCochable {

    private ArrayList<BoutonCochable> liste;

    public GroupeCochable() {
        liste = new ArrayList<>();
    }

    public GroupeCochable(ArrayList<BoutonCochable> liste) {
        this.liste = liste;
    }

    public void render(Graphics g) {
        for (int i = 0; i < liste.size(); i++) {
            liste.get(i).render(g);
        }
    }

    public void isInLayout(float x, float y) {

        for (int i = 0; i < liste.size(); i++) {
            if (liste.get(i).isInLayout(x, y)) {
                liste.get(i).setCocher(true);
            } else {
                liste.get(i).setCocher(false);
            }
        }


    }

    public ArrayList<BoutonCochable> getListe() {
        return liste;
    }

    public void setListe(ArrayList<BoutonCochable> liste) {
        this.liste = liste;
    }
}
