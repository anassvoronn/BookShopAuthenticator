package org.nastya.dto;

public class ProcessingResponse {
    private String status;
    private String sessionId;

    public ProcessingResponse(String status, String sessionId) {
        this.status = status;
        this.sessionId = sessionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
