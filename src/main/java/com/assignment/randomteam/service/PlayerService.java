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

    /**
     * Method to create the player.
     *
     * @param player player entity
     * @return Player entity
     */
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    /**
     * Method to get all players
     *
     * @return List of players
     */
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    /**
     * Method to update the player.
     *
     * @param id player's id
     * @param updatedPlayer player entity
     * @return Player entity
     */
    public Player updatePlayer(Long id, Player updatedPlayer) {
        Player existing = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player not found"));
        existing.setName(updatedPlayer.getName());
        existing.setSkillLevel(updatedPlayer.getSkillLevel());
        existing.setTeam(updatedPlayer.getTeam());
        return playerRepository.save(existing);
    }

    /**
     * Method to delete a player.
     *
     * @param id player's id
     */
    public void deletePlayer(Long id) {
        if (!playerRepository.existsById(id)) {
            throw new EntityNotFoundException("Player not found");
        }
        playerRepository.deleteById(id);
    }
}
