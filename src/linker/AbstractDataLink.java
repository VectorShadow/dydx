package linker;

import level.Level;
import player.Account;
import player.PlayerCharacter;

public abstract class AbstractDataLink extends Thread implements DataLink{

    private boolean terminated = false;

    /**
     * Each link is connected on a particular game level.
     * Both Server and Client interfaces require a method called getLevel() to reference this connection.
     * We implement that below by returning this field.
     */
    protected Level level;

    /**
     * Each link is connected for a particular player account.
     */
    protected Account account;

    /**
     * And one character for this account is in use.
     */
    protected PlayerCharacter character;

    @Override
    public abstract void run();

    @Override
    public void terminate() {
        terminated = true;
    }

    @Override
    public boolean isTerminated() {
        return terminated;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public void setLevel(Level l) {
        level = l;
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public void setAccount(Account a) {
        account = a;
    }

    @Override
    public PlayerCharacter getCharacter() {
        return character;
    }

    @Override
    public void setCharacter(PlayerCharacter pc) {
        character = pc;
    }
}
