package gui.draw;

import resources.DualityMode;
import resources.chroma.Chroma;
import resources.continuum.Pair;
import resources.glyph.Glyph;
import resources.glyph.GlyphBuilder;
import resources.glyph.ProtoGlyph;
import resources.glyph.ascii.SimpleGlyph;

import java.awt.*;
import java.util.ArrayList;

public enum Aspect {
    NONE,
    MEMORY,
    SOUND,
    VAGUE,
    DARK,
    INFRA,
    DIM,
    ULTRA,
    BRIGHT;

    public Glyph drawGlyph(Drawable d, DrawData dd) {
        //todo - handle reflection properties if extant. dd contains the light to reflect -
        // use GlyphBuilder.addReflection() on drawable d's Aspect - Reflection attribute
        ProtoGlyph protoGlyph = d.getProtoGlyph();
        ProtoGlyph adjustedProtoGlyph =
                dd.getBrightness() == Light.LIGHT_BRIGHT ? protoGlyph :
                        dd.getBrightness() == Light.LIGHT_DIM ? protoGlyph.dim() :
                                dd.getBrightness() == Light.LIGHT_DARK ? protoGlyph.dark() :
                                        null;
        ArrayList<Pair<Color>> tempColors = d.getAllTemporaryColors();
        ArrayList<Pair<Color>> adjustedTemp = new ArrayList<>();
        for (Pair<Color> cp : tempColors) {
            adjustedTemp.add(
                    new Pair<>(
                            cp.probability,
                            dd.getBrightness() == Light.LIGHT_BRIGHT ? cp.element :
                                    dd.getBrightness() == Light.LIGHT_DIM ? Chroma.dim(cp.element) :
                                            dd.getBrightness() == Light.LIGHT_DARK ? Chroma.dark(cp.element) :
                                                    Chroma.BLACK
                    )
            );
        }
        GlyphBuilder g = GlyphBuilder.buildGlyph();
        switch (this) {
            case MEMORY:
                //todo
                //memory aspects draws a bright grey foreground onto a background the color of the dark version of the foreground.
                //memory is drawn for terrain only.
                break;
            case SOUND:
                //todo
                //sound aspects use a profile that generates a sound based on the current game instant. A line is then drawn on the
                //sound graph from the source to the hearer. If the sound arrives with volume greater than the ambient background
                //noise AND the hearer's hearing theshold, a glyph will generate corresponding to the type of sound and the
                //difference in volume from whichever is higher - the background or the threshold - and the residual arrival volume
                break;
            case VAGUE:
                return g.readProtoGlyph(adjustedProtoGlyph, true).addStatusColors(adjustedTemp).build(DualityMode.TILE);
            case DARK: case DIM: case BRIGHT:
                return g.readProtoGlyph(adjustedProtoGlyph, false).addStatusColors(adjustedTemp).build(DualityMode.TILE);
            case INFRA:
                //todo
                break;
            case ULTRA:
                if (d.isUltraFluorescent()) return g.readProtoGlyph(protoGlyph.ultra(), false).build(DualityMode.TILE);
                break;

            //todo:
            //ensure we can draw both ascii and image glyphs here
            //todo:
            //switch on this to generate Glyphs or SubGlyphs to draw to the screen. All drawable items will have methods
            //which give instructions for drawing a glyph in certain conditions.

            //visible aspects draw from the base colors, scaled down depending on the effective brightness. These may also
            //include special visual indicators for effects like bleeding.
            //infra aspects offset ambient heat with drawable object heat - white hot or black hot, with a contrast defined
            //by the difference between ambient and drawable. This contrast goes both ways(so black hot will show the object
            //in a blacker color than the background if it is hotter, or whiter if it is cooler, and vice versa for white hot).
            //ultra aspects draw from the base colors, scaled up to ultra.
        }
        return SimpleGlyph.EMPTY_GLYPH;
    }

    public static Aspect get(int priority){
        switch (priority){
            case 0: return NONE;
            case 1: return MEMORY;
            case 2: return SOUND;
            case 3: return VAGUE;
            case 4: return DARK;
            case 5: return INFRA;
            case 6: return DIM;
            case 7: return ULTRA;
            case 8: return BRIGHT;
            default: throw new IllegalArgumentException("Invalid Aspect priority " + priority);
        }
    }
}
