package linker.local;

import data.AbstractDatum;
import data.handler.ClientHandler;
import linker.ClientDataLink;

public class ClientLocalDataLink extends AbstractLocalDataLink implements ClientDataLink {
    @Override
    public void handle(byte instruction, AbstractDatum datum) {
        ClientHandler.getInstance().handle(instruction, datum, this);
    }
}
