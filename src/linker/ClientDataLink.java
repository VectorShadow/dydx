package linker;

import level.Level;
import player.Account;

public interface ClientDataLink {
    Level getLevel();
    Account getAccount();
    void send(byte[] data);
}
