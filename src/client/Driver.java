package client;

import contract.Gui;
import core.DualityGUI;
import crypto.Cipher;
import data.DataPacker;
import data.InstructionCode;
import data.UserDatum;
import engine.Engine;
import error.ErrorLogger;
import graph.Coordinate;
import graph.Graph;
import gui.Display;
import level.BasicTerrainLookupTable;
import level.Level;
import linker.AbstractDataLink;
import resources.DualityContext;
import resources.DualityMode;
import resources.glyph.Glyph;
import resources.glyph.GlyphBuilder;
import resources.continuum.Pair;
import resources.glyph.ProtoGlyph;
import resources.glyph.ProtoGlyphBuilder;
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
            //if connection is established, create a datalink bound to the connection socket.
            Level.setTerrainLookupTable(new BasicTerrainLookupTable());
//            Cipher.testAllCrypto();
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
            Display.loadGraphics("./gfx/32.png", "./gfx/16.png");
            Display.getInstance().start();
            Display.drawLevel(adl.getLevel());
            Graph g0 = new Graph(adl.getLevel(), BasicTerrainLookupTable.flag(BasicTerrainLookupTable.PERMIT_MOVEMENT), true);
            Graph g1 = new Graph(adl.getLevel(), BasicTerrainLookupTable.flag(BasicTerrainLookupTable.PERMIT_LIGHT), false);
            System.out.println("Movement graph: " + g0 + "\nLight graph: " + g1);
            g0.update(new Coordinate(32, 32));
            System.out.println("Updated movement graph: " + g0);
            Graph.speedTest();
//            Gui gui = new DualityGUI();
//            ImageManager.loadGraphics(DualityContext.TILE_FULLSCREEN, new File("./gfx/32.png"));
//            ImageManager.loadGraphics(DualityContext.TILE_WINDOWED, new File("./gfx/16.png"));
//            ProtoGlyph player = ProtoGlyphBuilder.setDefaults('@', Color.BLACK, Color.WHITE).build();
//            gui.print(20, 20, GlyphBuilder.build(player));
//            ProtoGlyph question = ProtoGlyphBuilder.setDefaults('?', Color.BLACK, Color.MAGENTA, Color.YELLOW, Color.CYAN).addPrimary(new Pair<>(0.5, Color.ORANGE)).setSourceCoordinates(0,0).build();
//            gui.print(20, 21, GlyphBuilder.build(question, DualityMode.TILE));
//            gui.addZone(0.15, 0.33, 0.75, 0.1, DualityMode.TEXT);
//            ProtoGlyph bg = ProtoGlyphBuilder.setDefaults(' ', Color.DARK_GRAY, Color.WHITE).build();
//            ProtoGlyph border = ProtoGlyphBuilder.setDefaults('#', Color.DARK_GRAY, Color.RED).addBackground(new Pair<>(0.5, Color.LIGHT_GRAY)).addPrimary(new Pair<>(0.33, Color.GREEN)).addPrimary(new Pair<>(0.67, Color.BLUE)).build();
//            gui.setBorder(0, GlyphBuilder.build(border));
//            gui.print(
//                    0, 1, 1, GlyphBuilder.build(
//                    ProtoGlyphBuilder.setDefaults('@', Color.BLUE, Color.YELLOW).addPrimary(new Pair<>(0.33, Color.RED)).build()
//                    )
//            );
//            ArrayList<Glyph> testGlyphString = new ArrayList<>();
//            testGlyphString.add(GlyphBuilder.build(ProtoGlyphBuilder.setDefaults('H', Color.BLACK, Color.WHITE).build().insertPrimary(new Pair<>(0.5, Color.RED))));
//            testGlyphString.add(GlyphBuilder.build(ProtoGlyphBuilder.setDefaults('e', Color.BLACK, Color.WHITE).build()));
//            testGlyphString.add(GlyphBuilder.build(ProtoGlyphBuilder.setDefaults('l', Color.BLACK, Color.WHITE).build()));
//            testGlyphString.add(GlyphBuilder.build(ProtoGlyphBuilder.setDefaults('l', Color.BLACK, Color.WHITE).build()));
//            testGlyphString.add(GlyphBuilder.build(ProtoGlyphBuilder.setDefaults('o', Color.BLACK, Color.WHITE).build()));
//            testGlyphString.add(GlyphBuilder.build(ProtoGlyphBuilder.setDefaults(' ', Color.BLACK, Color.WHITE).build()));
//            testGlyphString.add(GlyphBuilder.build(ProtoGlyphBuilder.setDefaults('W', Color.BLACK, Color.WHITE).build().insertPrimary(new Pair<>(0.5, Color.GREEN)).insertPrimary(new Pair<>(0.5, Color.RED))));
//            testGlyphString.add(GlyphBuilder.build(ProtoGlyphBuilder.setDefaults('o', Color.BLACK, Color.WHITE).build()));
//            testGlyphString.add(GlyphBuilder.build(ProtoGlyphBuilder.setDefaults('r', Color.BLACK, Color.WHITE).build()));
//            testGlyphString.add(GlyphBuilder.build(ProtoGlyphBuilder.setDefaults('l', Color.BLACK, Color.WHITE).build()));
//            testGlyphString.add(GlyphBuilder.build(ProtoGlyphBuilder.setDefaults('d', Color.BLACK, Color.WHITE).build()));
//            testGlyphString.add(GlyphBuilder.build(ProtoGlyphBuilder.setDefaults('!', Color.BLACK, Color.WHITE).build()));
//            gui.print(0, 2, 2, testGlyphString);
            //gui.setFullScreen(false);
            Thread.sleep(750); //todo - handle this required pause better
            adl.send(DataPacker.pack(new UserDatum("Sereg", "pass1234"), InstructionCode.PROTOCOL_CREATE_ACCOUNT));
//            for (;;){
//                gui.redraw();
//                Thread.sleep(75);
//            }
            //todo - Engine.start() when ready to begin execution - local only... engine already running on server!
        } catch (Exception e) {
            ErrorLogger.logFatalException(ErrorLogger.trace(e));
        }
    }
}
