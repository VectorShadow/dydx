package server;

import actor.Actor;
import crypto.Password;
import error.ErrorLogger;
import player.Account;
import player.PlayerCharacter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/**
 * Maintains the file structure required by the server.
 * Handles predefined fileIO requirements such as account management.
 */
public class FileManager {
    public static final char SEPARATOR = '/';
    public static final String SEPARATOR_STRING = "" + SEPARATOR;

    private static final String DATA_FILE_EXTENSION = ".dis";
    private static final Path DATA_DIRECTORY = Paths.get("data");
    private static final Path USER_CATALOG = Paths.get(DATA_DIRECTORY + "/usercat" + DATA_FILE_EXTENSION);

    private static final Path ACCOUNT_DIRECTORY = Paths.get(DATA_DIRECTORY + SEPARATOR_STRING + "accounts");

    /**
     * Startup call to ensure file structure exists - if not, create it.
     */
    public static void ensurePaths() {
        try {
            if (!Files.exists(DATA_DIRECTORY)) Files.createDirectory(DATA_DIRECTORY);
            if (!Files.exists(ACCOUNT_DIRECTORY)) Files.createDirectory(ACCOUNT_DIRECTORY);
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
     * Create a new user account with the specified username and password and return that account.
     */
    public static Account createUser(String username, String password) {
        String salt = Password.generateRandomSalt();
        String catalogLine =
                username + SEPARATOR + salt + SEPARATOR + Password.hash(Password.salt(salt, password)) + "\n";
        try {
            Files.write(USER_CATALOG, catalogLine.getBytes(), StandardOpenOption.APPEND);
            Path userdir = getUserAccountDirectoryPath(username);
            if (!Files.exists(userdir)) Files.createDirectory(userdir);
            Path characterListPath = getUserAccountCharacterList(username);
            if (!Files.exists(characterListPath)) Files.createFile(characterListPath);
            return new Account(username, new ArrayList<>());
        } catch (IOException ioe) {
            ErrorLogger.logFatalException(ErrorLogger.trace(ioe));
            return null; //unreachable, since ErrorLogging terminates the thread, but required by Java
        }
    }
    /**
     * Verify an existing user account and load the account information.
     * If verification fails, return null.
     */
    public static Account loadUser(String username, String password) {
        if (!authenticateUser(username, password)) return null;
        return new Account(username, listCharacters(username));
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

    /**
     * Return the path to the data directory associated with the provided account name.
     */
    public static Path getUserAccountDirectoryPath(String username) {
        return Paths.get(ACCOUNT_DIRECTORY + SEPARATOR_STRING + username);
    }

    /**
     * Return the path to the character list, used to create an account object, associated with the provided account name.
     */
    public static Path getUserAccountCharacterList(String username) {
        return Paths.get(getUserAccountDirectoryPath(username) + "/charlist" + DATA_FILE_EXTENSION);
    }

    /**
     * Return the path to the character file associated with the provided account and character name.
     */
    private static Path getCharacterFilePath(String username, String characterName) {
        return Paths.get(
                getUserAccountDirectoryPath(username) + SEPARATOR_STRING + characterName + DATA_FILE_EXTENSION);
    }
    /**
     * Return the path to the actor file associated with the provided account and character name.
     */
    private static Path getActorFilePath(String username, String characterName) {
        return Paths.get(
                getUserAccountDirectoryPath(username) + SEPARATOR_STRING + characterName + ".actor" +
                        DATA_FILE_EXTENSION);
    }

    /**
     * Return a list of Character names associated with a provided account name.
     */
    public static ArrayList<String> listCharacters(String username) {
        ArrayList<String> characters = new ArrayList<>();
        try {
            BufferedReader fileIn = Files.newBufferedReader(getUserAccountCharacterList(username));
            String characterLine;
            while((characterLine = fileIn.readLine()) != null)
                characters.add(characterLine);
        } catch (IOException ioe) {
            ErrorLogger.logFatalException(ErrorLogger.trace(ioe));
        }
        return characters;
    }

    private static void writeByLine(File file, ArrayList<String> text, boolean append) throws IOException {
        FileWriter fw = new FileWriter(file, append);
        BufferedWriter bw = new BufferedWriter(fw);
        for (String s : text)
            bw.write(s + "\n");
        bw.close();
        fw.close();
    }
    private static ArrayList<String> readByLine(Path path) throws IOException {
        ArrayList<String> fileLines = new ArrayList<>();
        BufferedReader fileIn = Files.newBufferedReader(path);
        String fileLine;
        while((fileLine = fileIn.readLine()) != null)
            fileLines.add(fileLine);
        return fileLines;
    }
    /**
     * Add the provided character name to the character list for the provided account name.
     */
    public static void appendNewCharacter(String username, PlayerCharacter pc) {
        String characterName = pc.getName();
        ArrayList<String> currentCharacters = listCharacters(username);
        currentCharacters.add(characterName);
        ArrayList<String> existingCharacters = listCharacters(username);
        existingCharacters.add(characterName);
        try {
            Path userCharacterListPath = getUserAccountCharacterList(username);
            writeByLine(userCharacterListPath.toFile(), existingCharacters, false);
            saveCharacter(username, pc);
        } catch (IOException e) {
            ErrorLogger.logFatalException(ErrorLogger.trace(e));
        }
    }
    public static void saveCharacter(String username, PlayerCharacter pc) {
        String characterName = pc.getName();
        Path characterFilePath = getCharacterFilePath(username, characterName);
        Path actorFilePath = getActorFilePath(username, characterName);
        try {
            if (!Files.exists(characterFilePath)) Files.createFile(characterFilePath);
            if (!Files.exists(actorFilePath)) Files.createFile(actorFilePath);
            writeByLine(characterFilePath.toFile(), pc.saveAsText(), false);
            writeByLine(actorFilePath.toFile(), pc.getActor().saveAsText(), false);
        } catch (IOException e) {
            ErrorLogger.logFatalException(ErrorLogger.trace(e));
        }
    }
    public static PlayerCharacter loadCharacter(String username, String characterName) {
        Path characterFilePath = getCharacterFilePath(username, characterName);
        Path actorFilePath = getActorFilePath(username, characterName);
        try {
            return new PlayerCharacter(readByLine(characterFilePath), new Actor(readByLine(actorFilePath)));
        } catch (Exception e) {
            ErrorLogger.logFatalException(ErrorLogger.trace(e));
            return null; //useless and unreachable but required by java
        }
    }
}
