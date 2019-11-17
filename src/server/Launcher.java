package server;

import engine.Engine;
import error.ErrorLogger;

/**
 * executable for starting the server program as a remote server
 */
public class Launcher {

    public static void main(String[] args) {
        try {
            new Engine(true, true);
            //todo
        } catch (Exception e) {
            ErrorLogger.logFatalException(ErrorLogger.trace(e));
        }
    }
}
