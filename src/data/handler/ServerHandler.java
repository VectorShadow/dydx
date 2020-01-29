package data.handler;

import crypto.RSA;
import data.*;
import engine.CoreProcesses;
import error.ErrorLogger;
import error.LogReadyTraceableException;
import level.Level;
import linker.AbstractDataLink;
import mapgen.FloorDesigner;
import mapgen.WorldCoordinate;
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
        StringDatum sd;
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
            case InstructionCode.PROTOCOL_TRANSMIT_CHARACTER:
                PlayerCharacter pc = null;
                if (datum instanceof CharacterDatum) {
                    CharacterDatum cd = (CharacterDatum)datum;
                    pc = cd.getCharacter();
                    adl.getAccount().getActiveCharacters().add(pc.getName());
                    FileManager.appendNewCharacter(adl.getAccount().getAccountName(), pc);
                } else if (datum instanceof StringDatum) {
                    sd = (StringDatum)datum;
                    pc = FileManager.loadCharacter(adl.getAccount().getAccountName(), sd.getValue());
                }
                adl.setCharacter(pc);
                Level level = CoreProcesses.getLevelAtWorldCoordinate(pc.getWorldCoordinate());
                adl.setLevel(level);
                CoreProcesses.trackLevel(level);
                level.placeActor(pc.getActor());
                adl.send(DataPacker.pack(new CharacterDatum(pc), InstructionCode.PROTOCOL_TRANSMIT_CHARACTER));
                byte[] levelBytes = DataPacker.pack(new LevelDatum(level), InstructionCode.PROTOCOL_TRANSMIT_FLOOR);
                adl.send(levelBytes);
                break;
            case InstructionCode.PROTOCOL_TRANSMIT_ACTION_EVENT:
                ActionEventDatum aed = (ActionEventDatum)datum;
                adl.getCharacter().getActor().queueAction(aed.getActionItem());
                break;
            //todo - more cases
            default:
                ErrorLogger.logFatalException(new LogReadyTraceableException("Improper instruction."));
        }
    }
}
