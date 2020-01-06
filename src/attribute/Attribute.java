package attribute;

import java.io.Serializable;

/**
 * Intended to replace flags.
 */
public interface Attribute extends Serializable {

    int DEFAULT_FLAT = 0;
    double DEFAULT_SCALE = 1.0;

    boolean isSet();
    int getFlat();
    double getAverage();
    double getScaling();
}
