package linker.remote;

import crypto.Cipher;
import crypto.RSA;
import data.AbstractDatum;
import data.InstructionCode;
import data.BigIntegerDatum;
import data.UserDatum;
import error.ErrorLogger;
import error.LogReadyTraceableException;
import linker.ServerDataLink;
import server.FileManager;

import java.net.Socket;

public class ServerRemoteDataLink extends AbstractRemoteDataLink implements ServerDataLink {

    /**
     * The shared secret for this session.
     */
    private String linkSecret;

    public ServerRemoteDataLink(Socket s) throws LogReadyTraceableException {
        super(s);
    }

    @Override
    public void handle(byte instruction, AbstractDatum datum) {
        switch (instruction){
            case InstructionCode.PROTOCOL_BIG_INTEGER:
                BigIntegerDatum bid = (BigIntegerDatum)datum;
                linkSecret = RSA.decrypt(bid.getKey()).toString(16);
                break;
            case InstructionCode.PROTOCOL_CREATE_ACCOUNT:
                UserDatum ud = (UserDatum)datum;
                if (FileManager.doesUserExist(ud.getUsername())) break; //todo - handle this better!
                FileManager.createUser(ud.getUsername(), ud.decryptPassword(linkSecret));
                break;
            //todo - more cases
            default:
                ErrorLogger.logFatalException(new LogReadyTraceableException("Improper instruction."));
        }
    }
}
