package linker;

import level.Level;
import player.Account;

public interface ServerDataLink {
    Level getLevel();
    Account getAccount();
    void send(byte[] data);
}
