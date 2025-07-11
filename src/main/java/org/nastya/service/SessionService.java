package org.nastya.service;

import org.nastya.lib.auth.dto.SessionDTO;
import org.nastya.entity.Session;
import org.nastya.enums.SessionStatus;
import org.nastya.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionService {
    private static final Logger log = LoggerFactory.getLogger(SessionService.class);
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    public SessionService(SessionRepository sessionRepository, SessionMapper sessionMapper) {
        this.sessionRepository = sessionRepository;
        this.sessionMapper = sessionMapper;
    }

    public Optional<SessionDTO> findBySessionId(String sessionId) {
        log.info("Finding session by sessionId: {}", sessionId);
        Optional<SessionDTO> sessionDTO = sessionRepository.findBySessionId(sessionId)
                .map(sessionMapper::mapToSessionFormDTO);
        log.info("Session found: {}", sessionDTO.isPresent() ? sessionDTO.get() : "No session found");
        return sessionDTO;
    }

    public void deleteBySessionId(String sessionId) {
        log.info("Deleting session with sessionId: {}", sessionId);
        sessionRepository.deleteBySessionId(sessionId);
        log.info("Session with sessionId {} deleted", sessionId);
    }

    public SessionDTO saveSession(SessionDTO sessionDTO) {
        log.info("Saving session: {}", sessionDTO);
        Session session = sessionMapper.mapToSession(sessionDTO);
        Session savedSession = sessionRepository.save(session);
        log.info("Session saved: {}", savedSession);
        return sessionMapper.mapToSessionFormDTO(savedSession);
    }

    public boolean isSessionActive(String sessionId) {
        log.info("Checking if session is active for sessionId: {}", sessionId);
        return sessionRepository.findBySessionId(sessionId)
                .map(Session::isActive)
                .orElse(false);
    }

    public Optional<SessionDTO> findSessionByUserId(int userId) {
        List<Session> sessions = sessionRepository.findByUserIdAndStatus(userId, SessionStatus.ACTIVE);
        if (!sessions.isEmpty()) {
            Session session = sessions.get(0);
            SessionDTO sessionDTO = sessionMapper.mapToSessionFormDTO(session);
            return Optional.of(sessionDTO);
        }
        return Optional.empty();
    }
}
