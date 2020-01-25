package gui.draw;

import graph.Coordinate;
import resources.continuum.Pair;
import resources.glyph.ProtoGlyph;

import java.awt.*;
import java.util.ArrayList;

public interface Drawable {
    //todo - other stuff here to facilitate drawGlyph - symbol lists, color lists, status enhancements, etc.
    // especially, we ought to be able to check for ultrafluorescence, heat, and sounds here, since drawing needs these
    // probably we will need to implementation extend actors and terrain properties

    ProtoGlyph getProtoGlyph();

    ArrayList<Pair<Color>> getAllTemporaryColors();

    boolean isUltraFluorescent();

    //todo - heat
    //todo - sound
}
