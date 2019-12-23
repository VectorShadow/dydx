package data.handler;

import data.AbstractDatum;
import linker.AbstractDataLink;

public abstract class AbstractHandler {
    protected ImplementationHandler implementationHandler;

    public abstract void handle(int instruction, AbstractDatum datum, AbstractDataLink adl);

    void setImplementationHandler(ImplementationHandler ih) {
        implementationHandler = ih;
    }
}
