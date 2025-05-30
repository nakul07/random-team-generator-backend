package com.assignment.randomteam.service;

import com.assignment.randomteam.entity.Player;
import com.assignment.randomteam.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player updatePlayer(Long id, Player updatedPlayer) {
        Player existing = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player not found"));
        existing.setName(updatedPlayer.getName());
        existing.setSkillLevel(updatedPlayer.getSkillLevel());
        existing.setTeam(updatedPlayer.getTeam());
        return playerRepository.save(existing);
    }

    public void deletePlayer(Long id) {
        if (!playerRepository.existsById(id)) {
            throw new EntityNotFoundException("Player not found");
        }
        playerRepository.deleteById(id);
    }
}
