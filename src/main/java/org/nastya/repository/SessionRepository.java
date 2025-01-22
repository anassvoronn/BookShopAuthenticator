package org.nastya.repository;

import org.nastya.entity.Session;
import org.nastya.enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Query("UPDATE Session SET status = 'EXPIRED' WHERE status = 'ACTIVE' AND time < :expirationTime")
    int expireActiveSessions(@Param("expirationTime") LocalDateTime expirationTime);

    @Query("SELECT s FROM Session s WHERE s.userId = :userId AND s.status = :status")
    List<Session> findActiveSessionByUserId(@Param("userId") int userId, @Param("status") SessionStatus status);
}
