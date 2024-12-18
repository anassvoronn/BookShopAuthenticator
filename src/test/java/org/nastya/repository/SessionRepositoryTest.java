package org.nastya.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nastya.entity.Session;
import org.nastya.entity.User;
import org.nastya.enums.SessionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SessionRepositoryTest {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        User user1 = new User("userTest_1", "passwordTest_1");
        User user2 = new User("userTest_2", "passwordTest_2");
        User user3 = new User("userTest_3", "passwordTest_3");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }

    @Test
    public void testTwoActiveSessionsForSameUser() {
        int userId = userRepository.findAll().stream().findAny().get().getId();
        Session session1 = new Session(SessionStatus.ACTIVE, userId);
        sessionRepository.save(session1);
        Session session2 = new Session(SessionStatus.ACTIVE, userId);
        assertThrows(DataIntegrityViolationException.class, () -> {
            sessionRepository.save(session2);
        });
    }

    @Test
    public void testTwoExpiredSessionsForSameUser() {
        int userId = userRepository.findAll().stream().findAny().get().getId();
        Session session1 = new Session(SessionStatus.EXPIRED, userId);
        Session session2 = new Session(SessionStatus.EXPIRED, userId);

        sessionRepository.save(session1);
        sessionRepository.save(session2);

        List<Session> result = sessionRepository.findByUserIdAndStatus(userId, SessionStatus.EXPIRED);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(session -> session.getStatus() == SessionStatus.EXPIRED));
    }

    @Test
    public void testOneActiveAndOneExpiredSessionForSameUser() {
        int userId = userRepository.findAll().stream().findAny().get().getId();
        Session activeSession = new Session(SessionStatus.ACTIVE, userId);
        Session expiredSession = new Session(SessionStatus.EXPIRED, userId);

        sessionRepository.save(activeSession);
        sessionRepository.save(expiredSession);

        List<Session> activeSessions = sessionRepository.findByUserIdAndStatus(userId, SessionStatus.ACTIVE);
        List<Session> expiredSessions = sessionRepository.findByUserIdAndStatus(userId, SessionStatus.EXPIRED);

        assertEquals(1, activeSessions.size());
        assertEquals(SessionStatus.ACTIVE, activeSessions.get(0).getStatus());

        assertEquals(1, expiredSessions.size());
        assertEquals(SessionStatus.EXPIRED, expiredSessions.get(0).getStatus());
    }

    @Test
    public void testSaveSessionWithoutStatus() {
        int userId = userRepository.findAll().stream().findAny().get().getId();
        Session session = new Session(null, userId);
        assertThrows(DataIntegrityViolationException.class, () -> {
            sessionRepository.save(session);
        });
    }

    @Test
    public void testSaveSessionWithoutId() {
        Session session = new Session(SessionStatus.ACTIVE, 0);
        assertThrows(DataIntegrityViolationException.class, () -> {
            sessionRepository.save(session);
        });
    }

    @Test
    public void testSessionForNonExistentUser() {
        deleteUserByIdIfExists(9999);
        Session session = new Session(SessionStatus.ACTIVE, 9999);
        assertThrows(DataIntegrityViolationException.class, () -> {
            sessionRepository.save(session);
        });
    }

    private void deleteUserByIdIfExists(int id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            // Do nothing!
        }
    }
}
