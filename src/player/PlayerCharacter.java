package player;

import mapgen.WorldCoordinate;
import static server.FileManager.SEPARATOR_STRING;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class PlayerCharacter implements Serializable {

    public static final int NAME_INDEX = 0;
    public static final int WORLD_COORDINATE_INDEX = NAME_INDEX + 1;
    public static final int SUPER_INDICES = WORLD_COORDINATE_INDEX + 1; //todo - keep updated if we add fields

    private String name;
    private WorldCoordinate worldCoordinate;

    public PlayerCharacter(){
        worldCoordinate = WorldCoordinate.ORIGIN;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WorldCoordinate getWorldCoordinate() {
        return worldCoordinate;
    }

    public void setWorldCoordinate(WorldCoordinate worldCoordinate) {
        this.worldCoordinate = worldCoordinate;
    }


    public ArrayList<String> saveAsText(){
        ArrayList<String> save = new ArrayList<>();
        save.add(NAME_INDEX + SEPARATOR_STRING + name);
        save.add(WORLD_COORDINATE_INDEX + SEPARATOR_STRING + worldCoordinate.getInstance() + SEPARATOR_STRING +
                worldCoordinate.getWorldLocation() + SEPARATOR_STRING + worldCoordinate.getLevelDepth());
        //todo - other fields as necessary. Update SUPER_INDICES!
        return save;
    }
}
