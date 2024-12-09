package org.nastya.lifeSession;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nastya.entity.Session;
import org.nastya.enums.SessionStatus;
import org.nastya.repository.SessionRepository;
import org.nastya.sessionLifeCycle.SessionKiller;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class SessionKillerTest {
    @Mock
    private SessionRepository sessionRepository;
    private SessionKiller sessionKiller;

    @BeforeEach
    public void setUp() {
        sessionKiller = new SessionKiller(sessionRepository, 1);
    }

    @Test
    public void testExpireOldSessions() {
        Session session1 = new Session();
        session1.setStatus(SessionStatus.ACTIVE);
        Session session2 = new Session();
        session2.setStatus(SessionStatus.ACTIVE);

        Mockito.when(sessionRepository.findExpiredSessions(any())).thenReturn(Arrays.asList(session1, session2));

        sessionKiller.expireOldSessions();

        Mockito.verify(sessionRepository).findExpiredSessions(any());
        assertFalse(session1.isActive());
        assertFalse(session2.isActive());
        Mockito.verify(sessionRepository).save(session1);
        Mockito.verify(sessionRepository).save(session2);
    }
}