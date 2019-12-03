package server;

import crypto.Password;
import error.ErrorLogger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Maintains the file structure required by the server.
 * Handles predefined fileIO requirements such as account management.
 */
public class FileManager {
    private static final char SEPARATOR = '/';

    private static final String DATA_FILE_EXTENSION = ".dis";
    private static final Path DATA_DIRECTORY = Paths.get("data");
    private static final Path USER_CATALOG = Paths.get(DATA_DIRECTORY + "/usercat" + DATA_FILE_EXTENSION);

    /**
     * Startup call to ensure file structure exists - if not, create it.
     */
    public static void ensurePaths() {
        try {
            if (!Files.exists(DATA_DIRECTORY)) Files.createDirectory(DATA_DIRECTORY);
            if (!Files.exists(USER_CATALOG)) Files.createFile(USER_CATALOG);
        } catch (IOException ioe) {
            ErrorLogger.logFatalException(ErrorLogger.trace(ioe));
        }
    }

    /**
     * Verify that the user catalog contains the specified username.
     * This should always be called prior to authenticateUser() or createUser().
     */
    public static boolean doesUserExist(String username) {
        return getUserCatalogLine(username) != null;
    }

    /**
     * Verify the password for the specified user.
     * Contains must be called prior to this method in order to provide distinct error messages.
     */
    public static boolean authenticateUser(String username, String password) {
        String line = getUserCatalogLine(username);
        line = line.substring(line.indexOf(SEPARATOR) + 1);
        String salt = line.substring(0, line.indexOf(SEPARATOR));
        String hash = line.substring(line.indexOf(SEPARATOR) + 1);
        String saltedPassword = Password.salt(salt, password);
        return hash.equals(Password.hash(saltedPassword));
    }
    /**
     * Create a new user account with the specified username and password.
     */
    public static void createUser(String username, String password) {
        String salt = Password.generateRandomSalt();
        String catalogLine =
                username + SEPARATOR + salt + SEPARATOR + Password.hash(Password.salt(salt, password)) + "\n";
        try {
            BufferedWriter fileOut = Files.newBufferedWriter(USER_CATALOG, StandardOpenOption.APPEND);
            fileOut.write(catalogLine);
        } catch (IOException ioe) {
            ErrorLogger.logFatalException(ErrorLogger.trace(ioe));
        }
    }
    /**
     * Returns the catalog line associated with the specified user.
     */
    public static String getUserCatalogLine(String username) {
        try {
            BufferedReader fileIn = Files.newBufferedReader(USER_CATALOG);
            String catalogLine;
            while((catalogLine = fileIn.readLine()) != null) {
                if (catalogLine.substring(0, catalogLine.indexOf(SEPARATOR)).equals(username)) return catalogLine;
            }
        } catch (IOException ioe) {
            ErrorLogger.logFatalException(ErrorLogger.trace(ioe));
        }
        return null;
    }
}
