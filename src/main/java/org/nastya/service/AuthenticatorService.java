package org.nastya.service;

import org.nastya.dto.AuthenticationResponseDTO;
import org.nastya.dto.SessionDTO;
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
                if (existingSessionOptional.isPresent()) {
                    SessionDTO existingSession = existingSessionOptional.get();
                    String newSessionId = UUID.randomUUID().toString();
                    existingSession.setSessionId(newSessionId);
                    sessionService.saveSession(existingSession);
                    log.info("Session updated for user: {}, new sessionId: {}", username, newSessionId);
                    return new AuthenticationResponseDTO(AuthenticationStatus.SUCCESS, newSessionId);
                } else {
                    SessionDTO newSession = new SessionDTO();
                    newSession.setSessionId(UUID.randomUUID().toString());
                    newSession.setUserId(userDTO.getId());
                    sessionService.saveSession(newSession);
                    log.info("New session created for user: {}, sessionId: {}", username, newSession.getSessionId());
                    return new AuthenticationResponseDTO(AuthenticationStatus.SUCCESS, newSession.getSessionId());
                }
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
