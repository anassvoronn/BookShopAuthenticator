package org.nastya.service.mapper;

import org.nastya.dto.SessionDTO;
import org.nastya.entity.Session;
import org.nastya.service.SessionMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class SessionMapperImpl implements SessionMapper {
    @Override
    public SessionDTO mapToSessionFormDTO(Session session) {
        Assert.notNull(session, "session must not be null");
        return new SessionDTO(session.getId(), session.getSessionId(), session.getUserId());
    }

    @Override
    public Session mapToSession(SessionDTO sessionDTO) {
        Assert.notNull(sessionDTO, "session must not be null");
        return new Session(sessionDTO.getId(), sessionDTO.getSessionId(), sessionDTO.getUserId());
    }
}
