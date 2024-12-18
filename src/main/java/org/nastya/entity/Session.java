package org.nastya.entity;

import org.nastya.enums.SessionStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String sessionId;
    private int userId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SessionStatus status;
    private LocalDateTime time;

    public Session(int id, String sessionId, int userId) {
        this.id = id;
        this.sessionId = sessionId;
        this.userId = userId;
        this.status = SessionStatus.ACTIVE;
        this.time = LocalDateTime.now();
    }

    public Session(SessionStatus status, int userId) {
        this.status = status;
        this.userId = userId;
        this.sessionId = UUID.randomUUID().toString();
        this.time = LocalDateTime.now();
    }

    public Session() {
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
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

    public boolean isActive(){
        return status == SessionStatus.ACTIVE;
    }
}
