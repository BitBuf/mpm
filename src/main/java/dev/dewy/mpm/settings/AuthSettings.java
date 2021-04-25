package dev.dewy.mpm.settings;

import dev.dewy.mpm.models.Account;

import java.util.List;

public class AuthSettings {
    private final List<Account> accounts;

    public AuthSettings(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
