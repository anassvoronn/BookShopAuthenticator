package org.nastya.service;

import org.nastya.dto.SessionDTO;
import org.nastya.entity.Session;
import org.nastya.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    public SessionService(SessionRepository sessionRepository, SessionMapper sessionMapper) {
        this.sessionRepository = sessionRepository;
        this.sessionMapper = sessionMapper;
    }

    public Optional<SessionDTO> findBySessionId(String sessionId) {
        return sessionRepository.findBySessionId(sessionId)
                .map(sessionMapper::mapToSessionFormDTO);
    }

    public void deleteBySessionId(String sessionId) {
        sessionRepository.deleteBySessionId(sessionId);
    }

    public SessionDTO saveSession(SessionDTO sessionDTO) {
        Session session = sessionMapper.mapToSession(sessionDTO);
        Session savedSession = sessionRepository.save(session);
        return sessionMapper.mapToSessionFormDTO(savedSession);
    }

    public boolean isSessionActive(String sessionId) {
        Optional<Session> session = sessionRepository.findBySessionId(sessionId);
        return session.isPresent();
    }
}
