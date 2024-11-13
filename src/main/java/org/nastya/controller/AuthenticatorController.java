package org.nastya.controller;

import org.nastya.dto.AuthenticationResponseDTO;
import org.nastya.dto.AuthenticatorRequestDTO;
import org.nastya.service.AuthenticatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authenticator")
public class AuthenticatorController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticatorService.class);
    private final AuthenticatorService authenticatorService;

    public AuthenticatorController(AuthenticatorService authenticatorService) {
        this.authenticatorService = authenticatorService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody AuthenticatorRequestDTO authRequest) {
        log.info("Received login request for username: {}", authRequest.getUsername());
        AuthenticationResponseDTO response = authenticatorService.login(
                authRequest.getUsername(),
                authRequest.getPassword()
        );
        log.info("Login {} for username: {}", response.getStatus(), authRequest.getUsername());
        return ResponseEntity.ok(response);
    }
}
