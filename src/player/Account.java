package player;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {

    public static final int MAX_CHARACTER_COUNT = 4;

    public static final String LOCAL_PASSWORD = "password";

    private String accountName;
    private ArrayList<String> activeCharacters;

    public Account(String username, ArrayList<String> characterList) {
        accountName = username;
        activeCharacters = characterList;
    }

    public String getAccountName() {
        return accountName;
    }
    public ArrayList<String> getActiveCharacters() {
        return activeCharacters;
    }
}
