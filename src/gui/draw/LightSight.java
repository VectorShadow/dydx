package gui.draw;

public class LightSight {
    Light light = null;
    short sight = 0;

    public Light getLight() {
        return light;
    }

    public short getSight() {
        return sight;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public void setSight(short sight) {
        this.sight = sight;
    }
}
