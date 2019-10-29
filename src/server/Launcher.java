package server;

import error.ErrorLogger;
import server.engine.WorldManager;

/**
 * executable for starting the server program as a remote server
 */
public class Launcher {

    public static void main(String[] args) {
        try {
            new WorldManager(true);
            //todo
        } catch (Exception e) {
            ErrorLogger.logFatalException(ErrorLogger.trace(e));
        }
    }
}
