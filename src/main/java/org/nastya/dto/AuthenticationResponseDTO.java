package org.nastya.dto;

import org.nastya.enums.AuthenticationStatus;

public class AuthenticationResponseDTO {
    private AuthenticationStatus status;
    private String sessionId;

    public AuthenticationResponseDTO(AuthenticationStatus status, String sessionId) {
        this.status = status;
        this.sessionId = sessionId;
    }

    public AuthenticationStatus getStatus() {
        return status;
    }

    public void setStatus(AuthenticationStatus status) {
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
