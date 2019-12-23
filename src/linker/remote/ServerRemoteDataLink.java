package linker.remote;

import crypto.Cipher;
import crypto.RSA;
import data.AbstractDatum;
import data.InstructionCode;
import data.BigIntegerDatum;
import data.UserDatum;
import data.handler.ServerHandler;
import error.ErrorLogger;
import error.LogReadyTraceableException;
import linker.ServerDataLink;
import server.FileManager;

import java.net.Socket;

public class ServerRemoteDataLink extends AbstractRemoteDataLink implements ServerDataLink {

    public ServerRemoteDataLink(Socket s) throws LogReadyTraceableException {
        super(s);
    }

    @Override
    public void handle(byte instruction, AbstractDatum datum) {
        ServerHandler.getInstance().handle(instruction, datum, this);
    }
}
