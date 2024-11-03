package org.nastya.dto;

public class AuthenticatorRequestDTO {
    private String username;
    private String password;

    public AuthenticatorRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthenticatorRequestDTO() {
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
}
