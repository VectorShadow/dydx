package server;

import error.ErrorLogger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Maintains the file structure required by the server.
 */
public class FileManager {
    private static final String DATA_FILE_EXTENSION = ".dis";
    private static final Path DATA_DIRECTORY = Paths.get("data");
    private static final Path USER_CATALOG = Paths.get(DATA_DIRECTORY + "/usercat" + DATA_FILE_EXTENSION);

    public static void ensurePaths() {
        try {
            if (!Files.exists(DATA_DIRECTORY)) Files.createDirectory(DATA_DIRECTORY);
            if (!Files.exists(USER_CATALOG)) Files.createFile(USER_CATALOG);
        } catch (IOException ioe) {
            ErrorLogger.logFatalException(ErrorLogger.trace(ioe));
        }
    }
}
