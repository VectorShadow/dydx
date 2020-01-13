package gui.draw;

import java.awt.*;

public class Light implements Comparable<Light>{

    public static final int MAX_BRIGHTNESS = 3;
    public static final int MAX_RADIUS = 64;

    private final Color color;
    private final int brightness;
    private final int radius;
    private final double flicker;

    public Light(Color c, int b, int r, double f) {
        color = c;
        brightness = b > MAX_BRIGHTNESS ? MAX_BRIGHTNESS : b < 0 ? 0 : b;
        radius = r > MAX_RADIUS ? MAX_RADIUS : r < 1 ? 1 : r;
        flicker = f > 1.0 ? 1.0 : f < 0.0 ? 0.0 : f;
    }

    public Color getColor() {
        return color;
    }

    public double getFlicker() {
        return flicker;
    }

    public int getBrightness() {
        return brightness;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public int compareTo(Light light) {
        int value = brightness - light.brightness;
        if (value == 0) value = radius - light.radius;
        if (value == 0) value = (int)(100 * (light.flicker - flicker));
        if (value == 0) value = color.getRed() - light.color.getRed();
        if (value == 0) value = color.getGreen() - light.color.getGreen();
        if (value == 0) value = color.getBlue() - light.color.getBlue();
        return value;
    }
}
