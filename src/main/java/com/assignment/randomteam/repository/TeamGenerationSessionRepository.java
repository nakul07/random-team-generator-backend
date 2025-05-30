package com.assignment.randomteam.repository;

import com.assignment.randomteam.entity.TeamGenerationSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TeamGenerationSessionRepository extends JpaRepository<TeamGenerationSession, Long> {
    Optional<TeamGenerationSession> findByPublicId(UUID publicId);
}
