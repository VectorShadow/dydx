package player;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {

    public static final String LOCAL_PASSWORD = "password";

    private ArrayList<Character> activeCharacters = new ArrayList<>();

    public ArrayList<Character> getActiveCharacters() {
        return activeCharacters;
    }
}
