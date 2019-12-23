package data.handler;

import crypto.Cipher;
import crypto.RSA;
import data.AbstractDatum;
import data.BigIntegerDatum;
import data.DataPacker;
import data.InstructionCode;
import error.ErrorLogger;
import error.LogReadyTraceableException;
import linker.AbstractDataLink;

import java.math.BigInteger;

public class ClientHandler extends AbstractHandler {

    private static ClientHandler instance;

    public static ClientHandler getInstance() {
        if (instance == null) instance = new ClientHandler();
        return (ClientHandler)instance;
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
                BigInteger secretKey = new BigInteger(Cipher.getSessionKey(), 16);
                BigInteger encryptedSecretKey = RSA.encrypt(secretKey, bid.getKey());
                adl.send(
                        DataPacker.pack(new BigIntegerDatum(encryptedSecretKey), InstructionCode.PROTOCOL_BIG_INTEGER)
                );
                break;

            //todo - more cases
            default:
                ErrorLogger.logFatalException(new LogReadyTraceableException("Improper instruction."));
        }
    }
}
