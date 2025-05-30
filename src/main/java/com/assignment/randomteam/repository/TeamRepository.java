package com.assignment.randomteam.repository;

import com.assignment.randomteam.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}