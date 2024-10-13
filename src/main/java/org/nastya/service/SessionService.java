package org.nastya.service;

import org.nastya.entity.Session;
import org.nastya.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Optional<Session> findBySessionId(String sessionId) {
        return sessionRepository.findBySessionId(sessionId);
    }

    public void deleteBySessionId(String sessionId) {
        sessionRepository.deleteBySessionId(sessionId);
    }

    public Session saveSession(Session session) {
        return sessionRepository.save(session);
    }
}
