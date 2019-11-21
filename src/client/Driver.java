package client;

import contract.Gui;
import core.DualityGUI;
import crypto.Cipher;
import data.TestDatum;
import engine.Engine;
import error.ErrorLogger;
import linker.AbstractDataLink;
import data.StreamConverter;
import resources.DualityContext;
import resources.DualityMode;
import resources.glyph.Glyph;
import resources.glyph.GlyphBuilder;
import resources.continuum.Pair;
import resources.glyph.image.ImageManager;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Driver {
    /**
     * executable for starting the client program
     * @param args
     */
    public static void main(String[] args) {
        try {
            //open a GUI, attempt to connect to a remote server, and display information
            //if no connection can be established or player chooses to play locally, create a new Engine
            //and establish a datalink bound to the Engine's datalink.
            //if connection is establised, create a datalink bound to the connection socket.
            Cipher.testAllCrypto();
            /* test local */
            //realtime
            Engine e = new Engine(false, true);
            AbstractDataLink adl = e.generateClientDataLink();
            //turnbased
//            Engine e = new Engine(false, false);
//            AbstractDataLink adl = e.generateClientDataLink();
            /* end local */
            /* test remote */
//            AbstractDataLink adl = new Client().connect();
            /* end remote */
            adl.send(new byte[] {1, 0, 0, 5, 0, 1, 2, 3, 4});
            adl.send(new byte[] {7, 0, 0, 0x10, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f});
            TestDatum td = new TestDatum();
            System.out.println("" + (td.getTestString().equals(((TestDatum)(StreamConverter.toObject(StreamConverter.toByteArray(td)))).getTestString())));
            Gui gui = new DualityGUI();
            ImageManager.loadGraphics(DualityContext.TILE_FULLSCREEN, new File("./gfx/32.png"));
            ImageManager.loadGraphics(DualityContext.TILE_WINDOWED, new File("./gfx/16.png"));
            gui.print(20, 20, GlyphBuilder.buildGlyph().setDefaults(Color.BLACK, Color.WHITE, '@').build());
            gui.print(20, 21, GlyphBuilder.buildGlyph().setDefaults(Color.BLACK, Color.MAGENTA, Color.YELLOW, Color.CYAN, '?').addPrimaryColor(new Pair<>(0.5, Color.ORANGE)).build(0, 0, DualityMode.TILE));
            gui.addZone(0.15, 0.33, 0.75, 0.1, DualityMode.TEXT);
            gui.setBackground(0, GlyphBuilder.buildGlyph().setDefaults(Color.DARK_GRAY, Color.WHITE, ' ').build());
            gui.setBorder(0, GlyphBuilder.buildGlyph().setDefaults(Color.DARK_GRAY, Color.RED, '#').addBackgroundColor(new Pair<>(0.5, Color.LIGHT_GRAY)).addForegroundColor(new Pair<>(0.33, Color.GREEN)).addForegroundColor(new Pair<>(0.67, Color.BLUE)).build());
            gui.print(0, 1, 1, GlyphBuilder.buildGlyph().setDefaults(Color.BLUE, Color.YELLOW, '@').addForegroundColor(new Pair<>(0.33, Color.RED)).build());
            ArrayList<Glyph> testGlyphString = new ArrayList<>();
            testGlyphString.add(GlyphBuilder.buildGlyph().setDefaults(Color.BLACK, Color.WHITE, 'H').build());
            testGlyphString.add(GlyphBuilder.buildGlyph().setDefaults(Color.BLACK, Color.WHITE, 'e').build());
            testGlyphString.add(GlyphBuilder.buildGlyph().setDefaults(Color.BLACK, Color.WHITE, 'l').build());
            testGlyphString.add(GlyphBuilder.buildGlyph().setDefaults(Color.BLACK, Color.WHITE, 'l').build());
            testGlyphString.add(GlyphBuilder.buildGlyph().setDefaults(Color.BLACK, Color.WHITE, 'o').build());
            testGlyphString.add(GlyphBuilder.buildGlyph().setDefaults(Color.BLACK, Color.WHITE, ' ').build());
            testGlyphString.add(GlyphBuilder.buildGlyph().setDefaults(Color.BLACK, Color.WHITE, 'W').build());
            testGlyphString.add(GlyphBuilder.buildGlyph().setDefaults(Color.BLACK, Color.WHITE, 'o').build());
            testGlyphString.add(GlyphBuilder.buildGlyph().setDefaults(Color.BLACK, Color.WHITE, 'r').build());
            testGlyphString.add(GlyphBuilder.buildGlyph().setDefaults(Color.BLACK, Color.WHITE, 'l').build());
            testGlyphString.add(GlyphBuilder.buildGlyph().setDefaults(Color.BLACK, Color.WHITE, 'd').build());
            testGlyphString.add(GlyphBuilder.buildGlyph().setDefaults(Color.BLACK, Color.WHITE, '!').build());
            gui.print(0, 2, 2, testGlyphString);
            for (;;){
                gui.redraw();
                Thread.sleep(25);
            }
            //todo - Engine.start() when ready to begin execution
        } catch (Exception e) {
            ErrorLogger.logFatalException(ErrorLogger.trace(e));
        }
    }
}
