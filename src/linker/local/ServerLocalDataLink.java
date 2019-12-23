package linker.local;

import data.AbstractDatum;
import data.handler.ServerHandler;
import linker.ServerDataLink;

public class ServerLocalDataLink extends AbstractLocalDataLink implements ServerDataLink {
    @Override
    public void handle(byte instruction, AbstractDatum datum) {
        ServerHandler.getInstance().handle(instruction, datum, this);
    }
}
