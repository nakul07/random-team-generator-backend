package com.assignment.randomteam.repository;

import com.assignment.randomteam.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
