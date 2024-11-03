package org.nastya.dto;

public class SessionDTO {
    private int id;
    private String sessionId;
    private int userId;

    public SessionDTO(int id, String sessionId, int userId) {
        this.id = id;
        this.sessionId = sessionId;
        this.userId = userId;
    }

    public SessionDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
