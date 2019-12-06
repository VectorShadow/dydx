package level;

import effect.Effect;

import java.util.ArrayList;

public class TerrainProperties {

    /**
     * Predefined indices for light/sight and basic movement/item placement.
     * More specific indices may be defined by the implementation.
     */
    public static final int LIGHT_SIGHT = 0;
    public static final int WALK_DROP = 1;

    /**
     * Whether this terrain permits movement of type n.
     */
    private boolean[] permissions = new boolean[8];
    /**
     * Effects applied on moving into this terrain with movement of type n.
     */
    private ArrayList<ArrayList<Effect>> effects = new ArrayList<>();
    /**
     * Effects applied at intervals to actors and items occupying this terrain.
     */
    private ArrayList<Effect> stationaryEffects;
}
