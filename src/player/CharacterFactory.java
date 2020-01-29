package player;

import java.util.ArrayList;

public class CharacterFactory {
    private static CharacterManufactor characterManufactor;

    public static void setCharacterManufactor(CharacterManufactor cm) {
        characterManufactor = cm;
    }

    public static PlayerCharacter loadPlayerCharacter(ArrayList<String> characterFile, ArrayList<String> actorFile){
        return characterManufactor.loadPlayerCharacter(characterFile, actorFile);
    }
}
