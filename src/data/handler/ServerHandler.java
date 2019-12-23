package data.handler;

import crypto.RSA;
import data.AbstractDatum;
import data.BigIntegerDatum;
import data.InstructionCode;
import data.UserDatum;
import error.ErrorLogger;
import error.LogReadyTraceableException;
import linker.AbstractDataLink;
import server.FileManager;

public class ServerHandler extends AbstractHandler {

    protected static ServerHandler instance;

    /**
     * The shared secret for this session.
     */
    private String linkSecret;

    public static ServerHandler getInstance() {
        if (instance == null) instance = new ServerHandler();
        return (ServerHandler)instance;
    }

    @Override
    public void handle(int instruction, AbstractDatum datum, AbstractDataLink adl) {
        if (instruction > InstructionCode.DYDX_CODES) {
            implementationHandler.handle(instruction, datum, adl);
            return;
        }
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
