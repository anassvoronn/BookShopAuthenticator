package org.nastya.service;

import org.nastya.dto.AuthenticationResponseDTO;
import org.nastya.lib.auth.dto.SessionDTO;
import org.nastya.dto.UserDTO;
import org.nastya.enums.AuthenticationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticatorService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticatorService.class);
    private final UserService userService;
    private final SessionService sessionService;

    public AuthenticatorService(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    public AuthenticationResponseDTO login(String username, String password) {
        log.info("Attempting to log in user: {}", username);
        Optional<UserDTO> userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {
            UserDTO userDTO = userOptional.get();
            log.info("User found: {}", userDTO.getUsername());
            if (userDTO.getPassword().equals(password)) {
                log.info("Password is correct for user: {}", username);
                Optional<SessionDTO> existingSessionOptional = sessionService.findSessionByUserId(userDTO.getId());
                String newSessionId = UUID.randomUUID().toString();
                SessionDTO sessionDTO = existingSessionOptional
                        .map(dto -> new SessionDTO(dto, newSessionId))
                        .orElseGet(() -> new SessionDTO(0, newSessionId, userDTO.getId()));
                sessionService.saveSession(sessionDTO);
                log.info("New session Id created for user: {}, sessionId: {}", username, sessionDTO.getSessionId());
                return new AuthenticationResponseDTO(AuthenticationStatus.SUCCESS, sessionDTO.getSessionId());
            } else {
                log.warn("Invalid password for user: {}", username);
                return new AuthenticationResponseDTO(AuthenticationStatus.INVALID_PASSWORD, null);
            }
        } else {
            log.warn("User not found: {}", username);
            return new AuthenticationResponseDTO(AuthenticationStatus.USER_NOT_FOUND, null);
        }
    }
}
