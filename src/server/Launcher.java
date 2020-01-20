package server;

import engine.Engine;
import error.ErrorLogger;
import graph.Graph;
import theme.BasicThemeLookupTable;
import level.Level;

/**
 * executable for starting the server program as a remote server
 */
public class Launcher {

    public static void main(String[] args) {
        Level.setThemeLookupTable(new BasicThemeLookupTable());
        Graph.speedTest();
        try {
            new Engine(true, true);
            //todo
        } catch (Exception e) {
            ErrorLogger.logFatalException(ErrorLogger.trace(e));
        }
    }
}
