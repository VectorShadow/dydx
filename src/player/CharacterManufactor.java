package player;

import java.util.ArrayList;

public abstract class CharacterManufactor {
    public abstract PlayerCharacter loadPlayerCharacter(ArrayList<String> characterFile, ArrayList<String> actorFile);
}
