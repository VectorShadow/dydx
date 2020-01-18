package data.handler;

import crypto.RSA;
import data.*;
import error.ErrorLogger;
import error.LogReadyTraceableException;
import linker.AbstractDataLink;
import player.Account;
import player.PlayerCharacter;
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
                if (FileManager.doesUserExist(ud.getUsername())) {
                    Account account = FileManager.loadUser(ud.getUsername(),
                            ud.decryptPassword(linkSecret));
                    adl.setAccount(account);
                    adl.send(DataPacker.pack(new AccountDatum(account), InstructionCode.PROTOCOL_VERIFY_ACCOUNT));
                }
                else
                    adl.send(DataPacker.pack(new AccountDatum(null), InstructionCode.PROTOCOL_QUERY_ACCOUNT));
                break;
            case InstructionCode.PROTOCOL_CREATE_ACCOUNT:
                ud = (UserDatum)datum;
                if (FileManager.doesUserExist(ud.getUsername()))
                    throw new IllegalStateException("Tried to create existing user!");
                Account account = FileManager.createUser(ud.getUsername(),
                        ud.decryptPassword(linkSecret));
                adl.setAccount(account);
                adl.send(DataPacker.pack(new AccountDatum(account), InstructionCode.PROTOCOL_CREATE_ACCOUNT));
                break;
            case InstructionCode.PROTOCOL_QUERY_CHARACTER:
                //todo - check whether this character name already exists. Use for creation name check.
                // if so, transmit a Query Character code attached to an acknowledgement datum.
                // if not, transmit a Query Character code attached to a null character datum.
                break;
            case InstructionCode.PROTOCOL_REQUEST_CHARACTER:
                //todo - this should be a string datum. Unpack it, get the character file associated with the
                // string, and send it as a character datum with a transmit character code.
                // Use this when a player selects an existing character.
                break;
            case InstructionCode.PROTOCOL_TRANSMIT_CHARACTER:
                CharacterDatum cd = (CharacterDatum)datum;
                PlayerCharacter pc = cd.getCharacter();
                adl.setCharacter(pc);
                //todo - create a character file for this character in the logged in account's directory.
                break;
            //todo - more cases
            default:
                ErrorLogger.logFatalException(new LogReadyTraceableException("Improper instruction."));
        }
    }
}
