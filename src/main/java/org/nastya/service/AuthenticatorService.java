package org.nastya.service;

import org.nastya.dto.AuthenticationResponseDTO;
import org.nastya.dto.SessionDTO;
import org.nastya.dto.UserDTO;
import org.nastya.enums.AuthenticationStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticatorService {
    private final UserService userService;
    private final SessionService sessionService;

    public AuthenticatorService(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    public AuthenticationResponseDTO login(String username, String password) {
        Optional<UserDTO> userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {
            UserDTO userDTO = userOptional.get();
            if (userDTO.getPassword().equals(password)) {
                SessionDTO sessionDTO = new SessionDTO();
                sessionDTO.setSessionId(UUID.randomUUID().toString());
                sessionDTO.setUserId(userDTO.getId());
                sessionService.saveSession(sessionDTO);
                return new AuthenticationResponseDTO(AuthenticationStatus.SUCCESS, sessionDTO.getSessionId());
            } else {
                return new AuthenticationResponseDTO(AuthenticationStatus.INVALID_PASSWORD, null);
            }
        } else {
            return new AuthenticationResponseDTO(AuthenticationStatus.USER_NOT_FOUND, null);
        }
    }
}
