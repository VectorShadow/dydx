package data;

import player.PlayerCharacter;

public class CharacterDatum extends AbstractDatum {

    private final PlayerCharacter character;

    public CharacterDatum(PlayerCharacter pc) {
        character = pc;
    }

    public PlayerCharacter getCharacter() {
        return character;
    }
}
