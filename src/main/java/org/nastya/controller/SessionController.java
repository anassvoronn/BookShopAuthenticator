package org.nastya.controller;

import org.nastya.dto.SessionDTO;
import org.nastya.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/session")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/sessionId")
    public ResponseEntity<SessionDTO> getSessionById(@PathVariable String sessionId) {
        Optional<SessionDTO> session = sessionService.findBySessionId(sessionId);
        return session.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SessionDTO> createSession(@RequestBody SessionDTO sessionDTO) {
        SessionDTO savedSession = sessionService.saveSession(sessionDTO);
        return ResponseEntity.status(201).body(savedSession);
    }

    @DeleteMapping("/sessionId")
    public ResponseEntity<Void> deleteSession(@PathVariable String sessionId) {
        sessionService.deleteBySessionId(sessionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/sessionId")
    public ResponseEntity<Boolean> checkSessionStatus(@PathVariable String sessionId) {
        boolean isActive = sessionService.isSessionActive(sessionId);
        return ResponseEntity.ok(isActive);
    }
}
