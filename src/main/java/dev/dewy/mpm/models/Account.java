package dev.dewy.mpm.models;

public class Account {
    private final String email;
    private final String username;
    private final String password;
    private final String accessToken;
    private final String clientToken;

    public Account(String email) {
        this.email = email;
        this.username = null;
        this.password = null;
        this.accessToken = null;
        this.clientToken = null;
    }

    public Account(String email, String username, String password, String accessToken, String clientToken) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.accessToken = accessToken;
        this.clientToken = clientToken;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getClientToken() {
        return clientToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return email.equals(account.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
