package data;

import Player.Account;

public class AccountDatum extends AbstractDatum {

    private final Account account;

    public AccountDatum(Account a) {
        account = a;
    }
    public Account getAccount() {
        return account;
    }
}
