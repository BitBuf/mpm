package dev.dewy.mpm.models;

public class Account {
    private String email;
    private String username;
    private String password;
    private String accessToken;
    private String clientToken;

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

    public void setEmail(String email) {
        this.email = email;
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

        return email.equals(account.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
