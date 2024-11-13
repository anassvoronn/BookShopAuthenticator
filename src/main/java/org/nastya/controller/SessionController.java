package org.nastya.controller;

import org.nastya.dto.SessionDTO;
import org.nastya.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/session")
public class SessionController {
    private static final Logger log = LoggerFactory.getLogger(SessionService.class);
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<SessionDTO> getSessionById(@PathVariable String sessionId) {
        log.info("Received request to get session by ID: {}", sessionId);
        Optional<SessionDTO> session = sessionService.findBySessionId(sessionId);
        if (session.isPresent()) {
            log.info("Session found: {}", session.get());
            return ResponseEntity.ok(session.get());
        } else {
            log.warn("Session not found for ID: {}", sessionId);
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<SessionDTO> createSession(@RequestBody SessionDTO sessionDTO) {
        log.info("Received request to create session: {}", sessionDTO);
        SessionDTO savedSession = sessionService.saveSession(sessionDTO);
        log.info("Session created successfully: {}", savedSession);
        return ResponseEntity.status(201).body(savedSession);
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable String sessionId) {
        log.info("Received request to delete session with ID: {}", sessionId);
        sessionService.deleteBySessionId(sessionId);
        log.info("Session with ID {} deleted successfully", sessionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{sessionId}")
    public ResponseEntity<Boolean> checkSessionStatus(@PathVariable String sessionId) {
        log.info("Received request to check status for session ID: {}", sessionId);
        boolean isActive = sessionService.isSessionActive(sessionId);
        log.info("Session status for ID {}: {}", sessionId, isActive);
        return ResponseEntity.ok(isActive);
    }
}
