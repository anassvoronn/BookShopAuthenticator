package org.nastya.repository;

import org.nastya.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {

    Optional<Session> findBySessionId(String sessionId);

    void deleteBySessionId(String sessionId);
}
