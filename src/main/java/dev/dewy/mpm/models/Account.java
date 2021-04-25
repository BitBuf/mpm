package dev.dewy.mpm.models;

public class Account {
    private String username;
    private String password;
    private String accessToken;
    private String clientToken;

    public Account(String username) {
        this.username = username;
        this.password = null;
        this.accessToken = null;
        this.clientToken = null;
    }

    public Account(String username, String password, String accessToken, String clientToken) {
        this.username = username;
        this.password = password;
        this.accessToken = accessToken;
        this.clientToken = clientToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return username.equals(account.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
