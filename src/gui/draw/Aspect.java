package gui.draw;

import resources.glyph.Glyph;
import resources.glyph.GlyphBuilder;
import resources.glyph.ProtoGlyphBuilder;
import resources.glyph.ascii.SimpleGlyph;

import java.awt.*;

public enum Aspect {
    MEMORY,
    SOUND,
    VAGUE,
    DARK,
    INFRA,
    DIM,
    ULTRA,
    BRIGHT;

    public Glyph drawGlyph(Drawable d) {
        switch (this) {
            case BRIGHT: return GlyphBuilder.build(ProtoGlyphBuilder.setDefaults('!', Color.BLACK, Color.WHITE).build());
            //todo:
            //ensure we can draw both ascii and image glyphs here
            //todo:
            //switch on this to generate Glyphs or SubGlyphs to draw to the screen. All drawable items will have methods
            //which give instructions for drawing a glyph in certain conditions.
            //memory aspects draws a bright grey foreground onto a background the color of the dark version of the foreground.
            //memory is drawn for terrain only.
            //sound aspects use a profile that generates a sound based on the current game instant. A line is then drawn on the
            //sound graph from the source to the hearer. If the sound arrives with volume greater than the ambient background
            //noise AND the hearer's hearing theshold, a glyph will generate corresponding to the type of sound and the
            //difference in volume from whichever is higher - the background or the threshold - and the residual arrival volume
            //vague aspects are intended to indicate that something is present, but not what, in a visual sense overriding sound
            //visible aspects draw from the base colors, scaled down depending on the effective brightness. These may also
            //include special visual indicators for effects like bleeding. They also apply reflection properties if extant.
            //infra aspects offset ambient heat with drawable object heat - white hot or black hot, with a contrast defined
            //by the difference between ambient and drawable. This contrast goes both ways(so black hot will show the object
            //in a blacker color than the background if it is hotter, or whiter if it is cooler, and vice versa for white hot).
            //ultra aspects draw from the base colors, scaled up to ultra.
            default:
                return SimpleGlyph.EMPTY_GLYPH;
        }
    }

    public static Aspect get(int priority){
        switch (priority){
            case 0: return MEMORY;
            case 1: return SOUND;
            case 2: return VAGUE;
            case 3: return DARK;
            case 4: return INFRA;
            case 5: return DIM;
            case 6: return ULTRA;
            case 7: return BRIGHT;
            default: throw new IllegalArgumentException("Invalid Aspect priority " + priority);
        }
    }
}
