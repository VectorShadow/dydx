package linker;

import data.AbstractDatum;
import error.LogReadyTraceableException;
import level.Level;
import player.Account;
import player.PlayerCharacter;

public interface DataLink {
    Level getLevel();
    void setLevel(Level l);
    Account getAccount();
    void setAccount(Account a);
    PlayerCharacter getCharacter();
    void setCharacter(PlayerCharacter c);
    void terminate();
    boolean isTerminated();
    void send(byte[] data);
    void listen() throws LogReadyTraceableException;
    void handle(byte instruction, AbstractDatum datum);
}
