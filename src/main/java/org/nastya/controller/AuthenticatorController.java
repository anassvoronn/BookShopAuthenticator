package org.nastya.controller;

import org.nastya.dto.AuthenticatorRequest;
import org.nastya.dto.ProcessingResponse;
import org.nastya.entity.Session;
import org.nastya.entity.User;
import org.nastya.service.SessionService;
import org.nastya.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/authenticator")
public class AuthenticatorController {

    private final UserService userService;
    private final SessionService sessionService;

    public AuthenticatorController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticatorRequest authRequest) {
        Optional<User> userOptional = userService.findByUsername(authRequest.getUsername());
        Session session = new Session();
        if (userOptional.isPresent()) {
            session.setSession_id(UUID.randomUUID().toString());
            session.setUser_id(userOptional.get().getId());
            sessionService.saveSession(session);
            ProcessingResponse response = new ProcessingResponse("success", session.getSession_id());
            return ResponseEntity.ok(response);
        } else {
            ProcessingResponse errorResponse = new ProcessingResponse("error", session.getSession_id());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}
