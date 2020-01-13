package data.handler;

import crypto.RSA;
import data.*;
import error.ErrorLogger;
import error.LogReadyTraceableException;
import linker.AbstractDataLink;
import server.FileManager;

public class ServerHandler extends AbstractHandler {

    private static ServerHandler instance;

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
        UserDatum ud;
        switch (instruction){
            case InstructionCode.PROTOCOL_BIG_INTEGER:
                BigIntegerDatum bid = (BigIntegerDatum)datum;
                linkSecret = RSA.decrypt(bid.getKey()).toString(16);
                adl.send(DataPacker.pack(new AcknowledgementDatum(), InstructionCode.PROTOCOL_ACKNOWLEDGE_KEY_RECEIPT));
                break;
            case InstructionCode.PROTOCOL_QUERY_ACCOUNT:
                ud = (UserDatum)datum;
                if (FileManager.doesUserExist(ud.getUsername()))
                    adl.send(DataPacker.pack(new AccountDatum(FileManager.loadUser(ud.getUsername(),
                            ud.decryptPassword(linkSecret))), InstructionCode.PROTOCOL_VERIFY_ACCOUNT));
                else
                    adl.send(DataPacker.pack(new AccountDatum(null), InstructionCode.PROTOCOL_QUERY_ACCOUNT));
                break;
            case InstructionCode.PROTOCOL_CREATE_ACCOUNT:
                ud = (UserDatum)datum;
                if (FileManager.doesUserExist(ud.getUsername()))
                    throw new IllegalStateException("Tried to create existing user!");
                adl.send(DataPacker.pack(new AccountDatum(FileManager.createUser(ud.getUsername(),
                        ud.decryptPassword(linkSecret))), InstructionCode.PROTOCOL_CREATE_ACCOUNT));
                break;
            //todo - more cases
            default:
                ErrorLogger.logFatalException(new LogReadyTraceableException("Improper instruction."));
        }
    }
}
