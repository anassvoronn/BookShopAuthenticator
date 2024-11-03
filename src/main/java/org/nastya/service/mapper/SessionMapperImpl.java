package org.nastya.service.mapper;

import org.nastya.dto.SessionDTO;
import org.nastya.entity.Session;
import org.nastya.service.SessionMapper;
import org.springframework.stereotype.Component;

@Component
public class SessionMapperImpl implements SessionMapper {
    @Override
    public SessionDTO mapToSessionFormDTO(Session session) {
        if (session == null) {
            return null;
        }
        return new SessionDTO(session.getId(), session.getSessionId(), session.getUserId());
    }

    @Override
    public Session mapToSession(SessionDTO sessionDTO) {
        if (sessionDTO == null) {
            return null;
        }
        return new Session(sessionDTO.getId(), sessionDTO.getSessionId(), sessionDTO.getUserId());
    }
}
