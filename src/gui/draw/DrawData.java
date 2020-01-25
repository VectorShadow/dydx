package gui.draw;

public class DrawData {
    private int aspectRank;
    private int brightness;
    private Light light;

    public DrawData() {
        aspectRank = Aspect.NONE.ordinal();
        brightness = Light.LIGHT_BLACK;
        light = null;
    }

    public int getAspectRank() {
        return aspectRank;
    }

    public int getBrightness() {
        return brightness;
    }

    public Light getLight() {
        return light;
    }

    public void setValues(int ar, int b, Light l) {
        aspectRank = ar;
        brightness = b;
        light = l;
    }
}