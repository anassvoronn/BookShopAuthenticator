package org.nastya.enums;

public enum AuthenticationStatus {
    SUCCESS("success"),
    USER_NOT_FOUND("user not found"),
    INVALID_PASSWORD("invalid password"),
    ERROR("error");

    private final String status;

    AuthenticationStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
