package effect;

import java.util.ArrayList;

/**
 * Definable enumeration.
 * Use defineType to add elements, and EffectType.named() to access defined elements.
 * Comparable via ==, but we provide a .equals() method which evaluates as ==.
 */
public class EffectType {

    private static ArrayList<EffectType> types = new ArrayList<>();

    public static void defineType(String typeName){
        for (EffectType type : types) {
            if (type.name.equals(typeName)) throw new IllegalArgumentException("Type " + typeName + " already defined.");
        }
        final EffectType effectType = new EffectType(typeName);
        types.add(effectType);
    }

    public static EffectType named(String t){
        for (final EffectType effectType : types) {
            if (effectType.name.equals(t)) return effectType;
        }
        throw new IllegalArgumentException("Typename " + t + " has not been defined. " +
                "Call Effect.defineType(" + t + ") to define this type.");
    }

    private String name;

    private EffectType(String n) {
        name = n;
    }

    /**
     * Enable switching.
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof EffectType && this == o;
    }
}
