package effect;

import java.util.ArrayList;

/**
 * Definable enumeration.
 * Use defineType to add elements, and EffectType.named() to access defined elements.
 * Comparable via ==, but we provide a .equals() method which evaluates as ==.
 */
public class EffectType {

    private static ArrayList<EffectType> enumeration = new ArrayList<>();

    public static void defineType(String typeName){
        for (EffectType type : enumeration) {
            if (type.name.equals(typeName)) throw new IllegalArgumentException("Type " + typeName + " already defined.");
        }
        final EffectType effectType = new EffectType(typeName);
        enumeration.add(effectType);
    }

    public static EffectType named(String t){
        for (final EffectType effectType : enumeration) {
            if (effectType.name.equals(t)) return effectType;
        }
        throw new IllegalArgumentException("Typename " + t + " has not been defined. " +
                "Call EffectType.defineType(" + t + ") to define this type.");
    }
    public static int countDefinedEffectTypes() {
        return enumeration.size();
    }

    private final String name;

    private EffectType(String n) {
        name = n;
    }
    public int indexOf(){
        for (int i = 0; i < countDefinedEffectTypes(); ++i) {
            if (enumeration.get(i) == this) return i;
        }
        throw new IllegalStateException("Permission " + name + " not present in enumeration.");
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
