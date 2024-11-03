package org.nastya.controller;

import org.nastya.dto.AuthenticatorRequestDTO;
import org.nastya.dto.AuthenticationResponseDTO;
import org.nastya.enums.AuthenticationStatus;
import org.nastya.service.AuthenticatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authenticator")
public class AuthenticatorController {
    private final AuthenticatorService authenticatorService;

    public AuthenticatorController(AuthenticatorService authenticatorService) {
        this.authenticatorService = authenticatorService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticatorRequestDTO authRequest) {
        AuthenticationResponseDTO response = authenticatorService.login(authRequest.getUsername(), authRequest.getPassword());
        if (response.getStatus() == AuthenticationStatus.SUCCESS) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
