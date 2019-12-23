package data.handler;

import data.AbstractDatum;
import linker.AbstractDataLink;

/**
 * Implementations must extend this and load it into the Server and Client handler instances to provide support for
 * implementation specific instructions.
 */
public abstract class ImplementationHandler {
    abstract void handle(int instructionCode, AbstractDatum datum, AbstractDataLink adl);
}
