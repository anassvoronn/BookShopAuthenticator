package org.nastya.lifeSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nastya.entity.Session;
import org.nastya.entity.User;
import org.nastya.enums.SessionStatus;
import org.nastya.repository.SessionRepository;
import org.nastya.repository.UserRepository;
import org.nastya.sessionLifeCycle.SessionKiller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SessionKillerTest {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private SessionKiller sessionKiller;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        User user4 = new User("userTest_4", "passwordTest_4");
        User user5 = new User("userTest_5", "passwordTest_5");
        User user6 = new User("userTest_6", "passwordTest_6");

        userRepository.save(user4);
        userRepository.save(user5);
        userRepository.save(user6);
    }

    @Test
    public void testExpireOldSessions() {
        List<User> users = userRepository.findAll();
        int userId1 = users.get(0).getId();
        int userId2 = users.get(1).getId();
        int userId3 = users.get(2).getId();
        Session session1 = new Session(SessionStatus.ACTIVE, userId1);
        Session session2 = new Session(SessionStatus.ACTIVE, userId2);
        Session session3 = new Session(SessionStatus.ACTIVE, userId3);

        LocalDateTime creationTime = LocalDateTime.now().minusMinutes(20);
        session1.setTime(creationTime);
        session2.setTime(creationTime);
        session3.setTime(LocalDateTime.now().minusMinutes(10));

        sessionRepository.save(session1);
        sessionRepository.save(session2);
        sessionRepository.save(session3);

        sessionKiller.expireOldSessions();

        Optional<Session> expiredSession1 = sessionRepository.findById(session1.getId());
        Optional<Session> expiredSession2 = sessionRepository.findById(session2.getId());
        Optional<Session> expiredSession3 = sessionRepository.findById(session3.getId());

        assertTrue(expiredSession1.isPresent());
        assertEquals(SessionStatus.EXPIRED, expiredSession1.get().getStatus());

        assertTrue(expiredSession2.isPresent());
        assertEquals(SessionStatus.EXPIRED, expiredSession2.get().getStatus());

        assertTrue(expiredSession3.isPresent());
        assertEquals(SessionStatus.ACTIVE, expiredSession3.get().getStatus());

    }
}