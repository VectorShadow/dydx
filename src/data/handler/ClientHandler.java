package data.handler;

import client.SocketMonitor;
import crypto.Cipher;
import crypto.RSA;
import data.*;
import error.ErrorLogger;
import error.LogReadyTraceableException;
import linker.AbstractDataLink;

import java.math.BigInteger;

public class ClientHandler extends AbstractHandler {

    public static final int KEY_RECEIVED = 0;
    public static final int KEY_ACKNOWLEDGED = 1;

    public static final int NO_RESPONSE = -1;
    public static final int LOGIN_SUCCESS = 0;
    public static final int LOGIN_FAILURE = 1;
    public static final int NO_SUCH_ACCOUNT = 2;

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
        AccountDatum ad;
        switch (instruction){
            case InstructionCode.PROTOCOL_BIG_INTEGER:
                SocketMonitor.reportAcknowledgement(KEY_RECEIVED);
                BigIntegerDatum bid = (BigIntegerDatum)datum;
                BigInteger secretKey = new BigInteger(Cipher.getSessionKey(), 16);
                BigInteger encryptedSecretKey = RSA.encrypt(secretKey, bid.getKey());
                adl.send(
                        DataPacker.pack(new BigIntegerDatum(encryptedSecretKey), InstructionCode.PROTOCOL_BIG_INTEGER)
                );
                break;
            case InstructionCode.PROTOCOL_ACKNOWLEDGE_KEY_RECEIPT:
                SocketMonitor.reportAcknowledgement(KEY_ACKNOWLEDGED);
                break;
            case InstructionCode.PROTOCOL_QUERY_ACCOUNT:
                SocketMonitor.reportAcknowledgement(NO_SUCH_ACCOUNT);
                break;
            case InstructionCode.PROTOCOL_VERIFY_ACCOUNT:
                ad = (AccountDatum)datum; //todo - load the client side account from this!
                if (ad.getAccount() == null)
                    SocketMonitor.reportAcknowledgement(LOGIN_FAILURE);
                else
                    SocketMonitor.reportAcknowledgement(LOGIN_SUCCESS);
                break;
            case InstructionCode.PROTOCOL_CREATE_ACCOUNT:
                ad = (AccountDatum)datum; //todo - load the client side account from this!
                SocketMonitor.reportAcknowledgement(LOGIN_SUCCESS);
                break;
            //todo - more cases
            default:
                ErrorLogger.logFatalException(new LogReadyTraceableException("Improper instruction."));
        }
    }
}
