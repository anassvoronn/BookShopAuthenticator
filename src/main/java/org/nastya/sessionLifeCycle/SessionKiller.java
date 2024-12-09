package org.nastya.sessionLifeCycle;

import org.nastya.entity.Session;
import org.nastya.enums.SessionStatus;
import org.nastya.repository.SessionRepository;
import org.nastya.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SessionKiller {
    private static final Logger log = LoggerFactory.getLogger(SessionService.class);
    private final SessionRepository sessionRepository;
    private final int expirationMinutes;

    public SessionKiller(SessionRepository sessionRepository,
                         @Value("${session.expiration.minutes}") int expirationMinutes) {
        this.sessionRepository = sessionRepository;
        this.expirationMinutes = expirationMinutes;
    }

    private LocalDateTime getExpirationTime() {
        return LocalDateTime.now().minusMinutes(expirationMinutes);
    }

    @Scheduled(fixedRate = 60000)
    public void expireOldSessions() {
        LocalDateTime expirationTime = getExpirationTime();
        List<Session> expiredSessions = sessionRepository.findExpiredSessions(expirationTime);
        for (Session session : expiredSessions) {
            session.setStatus(SessionStatus.EXPIRED);
            sessionRepository.save(session);
        }
        log.info("Session statuses updated: {}", expiredSessions.size());
    }
}