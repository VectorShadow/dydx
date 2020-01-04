package gui;

import contract.Gui;
import core.DualityGUI;
import error.ErrorLogger;
import level.Level;
import resources.DualityContext;
import resources.DualityMode;
import resources.glyph.Glyph;
import resources.glyph.image.ImageManager;
import resources.render.OutputMode;

import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Display extends Thread {

    private static int FPS = 40;

    private static final int MIN_FPS = 25;
    private static final int MAX_FPS = 100;

    private static Display instance = null;

    public static void loadGraphics(String fullscreenImageFilePath, String windowedImageFilePath) throws IOException {
        ImageManager.loadGraphics(DualityContext.TILE_FULLSCREEN, new File(fullscreenImageFilePath));
        ImageManager.loadGraphics(DualityContext.TILE_WINDOWED, new File(windowedImageFilePath));
    }

    public static void setFPS(int FPS) {
        Display.FPS = FPS < MIN_FPS ? MIN_FPS : FPS > MAX_FPS ? MAX_FPS : FPS;
    }
    private static int refreshRate() {
        return 1000 / FPS;
    }

    public static Display getInstance() {
        if (instance == null) instance = new Display();
        return instance;
    }
    public static void setKeyListener(KeyListener kl) {
        getInstance().gui.addKeyListener(kl);
    }
    public static void setFullscreen(boolean fullscreen) {
        getInstance().gui.setFullScreen(fullscreen);
    }
    public static void toggleFullscreen() {
        getInstance().gui.toggleFullScreen();
    }

    public static void drawLevel(Level level) {
        WorldCanvas.paint(level, getInstance().gui);
    }

    public static int temporaryTextWindow(double pct) {
        return getInstance().gui.addZone((1.0 - pct) / 2, pct, (1.0 - pct) / 2, pct, DualityMode.TEXT) - 1;
    }
    public static void writeToTemporaryTextWindow(int zoneId, ArrayList<Glyph> glyphString) {
        getInstance().gui.print(zoneId, 1, 1, glyphString);
    }
    public static void disposeTemporaryTextWindow(int zoneID){
        getInstance().gui.removeZone(zoneID);
    }

    private final Gui gui = new DualityGUI();

    @Override
    public void run() {
        for (;;){
            gui.redraw();
            try {
                Thread.sleep(refreshRate());
            } catch (InterruptedException e) {
                ErrorLogger.logFatalException(ErrorLogger.trace(e));
//            } catch (ConcurrentModificationException cme) {
//                //do nothing? todo: find out if this causes serious problems or not
            }
        }
    }
}
