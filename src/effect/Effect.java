package effect;

import java.util.ArrayList;

public class Effect {

    private EffectType effectType;
    private double power;

    public Effect(EffectType et, double p) {
            effectType = et;
            power = p;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    public double getPower() {
        return power;
    }
}
