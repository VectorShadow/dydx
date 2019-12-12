package linker.local;

import crypto.Cipher;
import data.AbstractDatum;
import data.InstructionCode;
import data.UserDatum;
import error.ErrorLogger;
import error.LogReadyTraceableException;
import linker.ServerDataLink;
import server.FileManager;

public class ServerLocalDataLink extends AbstractLocalDataLink implements ServerDataLink {
    @Override
    public void handle(byte instruction, AbstractDatum datum) {
        switch (instruction){
            //todo - more cases
            case InstructionCode.PROTOCOL_CREATE_ACCOUNT:
                UserDatum ud = (UserDatum)datum;
                //todo - make sure we don't call this unless we've verified the account doesn't already exist!
                //decrypt using the local session key
                FileManager.createUser(ud.getUsername(), ud.decryptPassword());
                break;
            default:
                ErrorLogger.logFatalException(new LogReadyTraceableException("Improper instruction."));
        }
    }
}
