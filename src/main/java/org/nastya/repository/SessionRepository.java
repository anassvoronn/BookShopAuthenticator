package org.nastya.repository;

import org.nastya.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {

    Optional<Session> findBySessionId(String sessionId);

    void deleteBySessionId(String sessionId);

    @Query("SELECT s FROM Session s WHERE s.status = 'ACTIVE' AND s.time < :expirationTime")
    List<Session> findExpiredSessions(@Param("expirationTime") LocalDateTime expirationTime);
}
