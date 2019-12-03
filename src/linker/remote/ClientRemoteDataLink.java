package linker.remote;

import crypto.Cipher;
import crypto.RSA;
import data.AbstractDatum;
import data.DataPacker;
import data.InstructionCode;
import data.BigIntegerDatum;
import error.ErrorLogger;
import error.LogReadyTraceableException;
import linker.ClientDataLink;

import java.math.BigInteger;
import java.net.Socket;

public class ClientRemoteDataLink extends AbstractRemoteDataLink implements ClientDataLink {

    public ClientRemoteDataLink(Socket s) throws LogReadyTraceableException {
        super(s);
        Cipher.generateSessionKey(); //generate a secret key to encrypt the session with
    }

    @Override
    public void handle(byte instruction, AbstractDatum datum) {
        switch (instruction){
            case InstructionCode.PROTOCOL_BIG_INTEGER:
                BigIntegerDatum bid = (BigIntegerDatum)datum;
                /*test*/ System.out.println("\nRSAKey: " + bid.getKey() + " SessionSecret: " + Cipher.getSessionKey());
                BigInteger secretKey = new BigInteger(Cipher.getSessionKey(), 16);
                BigInteger encryptedSecretKey = RSA.encrypt(secretKey, bid.getKey());
                send(
                        DataPacker.pack(new BigIntegerDatum(encryptedSecretKey), InstructionCode.PROTOCOL_BIG_INTEGER)
                );
                break;

            //todo - more cases
            default:
                ErrorLogger.logFatalException(new LogReadyTraceableException("Improper instruction."));
        }
    }
}
