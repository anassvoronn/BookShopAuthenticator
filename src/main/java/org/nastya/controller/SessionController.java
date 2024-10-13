package org.nastya.controller;

import org.nastya.entity.Session;
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

    @GetMapping("/{session_id}")
    public ResponseEntity<Session> getSessionById(@PathVariable String sessionId) {
        Optional<Session> session = sessionService.findBySessionId(sessionId);
        return session.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Session> createSession(@RequestBody Session session) {
        Session savedSession = sessionService.saveSession(session);
        return ResponseEntity.status(201).body(savedSession);
    }

    @DeleteMapping("/{session_id}")
    public ResponseEntity<Void> deleteSession(@PathVariable String sessionId) {
        sessionService.deleteBySessionId(sessionId);
        return ResponseEntity.noContent().build();
    }
}
