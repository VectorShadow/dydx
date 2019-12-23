package linker.remote;

import data.AbstractDatum;
import data.handler.ClientHandler;
import error.LogReadyTraceableException;
import linker.ClientDataLink;

import java.net.Socket;

public class ClientRemoteDataLink extends AbstractRemoteDataLink implements ClientDataLink {

    public ClientRemoteDataLink(Socket s) throws LogReadyTraceableException {
        super(s);
    }

    @Override
    public void handle(byte instruction, AbstractDatum datum) {
        ClientHandler.getInstance().handle(instruction, datum, this);
    }
}
