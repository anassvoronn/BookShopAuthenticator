package org.nastya.service;

import org.nastya.lib.auth.dto.SessionDTO;
import org.nastya.entity.Session;

public interface SessionMapper {

    SessionDTO mapToSessionFormDTO(Session session);

    Session mapToSession(SessionDTO sessionDTO);
}
