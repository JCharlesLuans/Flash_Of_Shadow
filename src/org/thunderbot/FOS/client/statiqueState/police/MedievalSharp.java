package org.thunderbot.FOS.client.statiqueState.police;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class MedievalSharp {

    UnicodeFont font;

    public MedievalSharp(int fontSize) throws SlickException {
        font = new UnicodeFont("res/menuState/gui/police/MedievalSharp-Regular.ttf", fontSize, false, false);
        initEffet();
        initGliphs();
    }

    public UnicodeFont getFont() {
        return font;
    }

    public void setSize(int fontSize) {
        try {
            font = new UnicodeFont("res/menuState/gui/police/MedievalSharp-Regular.ttf", fontSize, false, false);
            initEffet();
            initGliphs();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    private void initGliphs() throws SlickException {
        font.addAsciiGlyphs();
        font.addGlyphs(400,600);
        font.loadGlyphs();
    }

    private void initEffet() {
        font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
    }
}
