package org.nastya.service;

import org.nastya.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

    @Transactional
    @Scheduled(fixedRate = 60000)
    public void expireOldSessions() {
        LocalDateTime expirationTime = getExpirationTime();
        int countExpiredSessions = sessionRepository.expireActiveSessions(expirationTime);
        log.info("Session statuses updated: {}", countExpiredSessions);
    }
}