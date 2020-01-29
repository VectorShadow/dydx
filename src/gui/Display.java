package gui;

import contract.Gui;
import core.DualityGUI;
import engine.CoreProcesses;
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

    private static boolean gfxAllowed = false;

    private static Display instance = null;

    private static long lastDraw = 0;

    public static void loadGraphics(String fullscreenImageFilePath, String windowedImageFilePath) throws IOException {
        ImageManager.loadGraphics(DualityContext.TILE_FULLSCREEN, new File(fullscreenImageFilePath));
        ImageManager.loadGraphics(DualityContext.TILE_WINDOWED, new File(windowedImageFilePath));
    }
    public static void setGfxAllowed(boolean allowed) {
        gfxAllowed = allowed;
    }
    public static boolean isGfxAllowed() {
        return gfxAllowed;
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

    public static void drawLevel() {
        long now = System.currentTimeMillis();
        if (now - lastDraw < refreshRate()) return;
        WorldCanvas.paint(CoreProcesses.getActiveLevel(), getInstance().gui);
        lastDraw = now;
    }

    public static Gui gui(){
        return getInstance().gui;
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
