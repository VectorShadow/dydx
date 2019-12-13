package gui;

import contract.Gui;
import core.DualityGUI;
import error.ErrorLogger;
import level.Level;
import resources.DualityContext;
import resources.glyph.image.ImageManager;

import java.io.File;
import java.io.IOException;

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

    public static void drawLevel(Level level) {
        WorldCanvas.paint(level, getInstance().gui);
    }

    private final Gui gui = new DualityGUI();

    @Override
    public void run() {
        gui.redraw();
        try {
            Thread.sleep(refreshRate());
        } catch (InterruptedException e) {
            ErrorLogger.logFatalException(ErrorLogger.trace(e));
        }
    }
}