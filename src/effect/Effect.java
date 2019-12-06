package effect;

import java.util.ArrayList;

public class Effect {
    private static ArrayList<String> types = new ArrayList<>();

    private int type;
    private double power;

    public Effect generateEffect(String t, double p){
        for (int i = 0; i < types.size(); ++i) {
            if (types.get(i).equals(t)) return new Effect(i, p);
        }
        throw new IllegalArgumentException("Typename " + t + " has not been defined. " +
                "Call Effect.defineType(" + t + ") to define this type.");
    }

    private Effect(int t, double p) {

    }
    public static void defineType(String typeName){
        for (String type : types) {
            if (type.equals(typeName)) throw new IllegalArgumentException("Type " + typeName + " already defined.");
        }
        types.add(typeName);
    }
}
