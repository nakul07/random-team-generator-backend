package com.assignment.randomteam.controller;

import com.assignment.randomteam.dto.PlayerDTO;
import com.assignment.randomteam.entity.Player;
import com.assignment.randomteam.entity.Team;
import com.assignment.randomteam.repository.TeamRepository;
import com.assignment.randomteam.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/players")
@CrossOrigin
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;
    private final TeamRepository teamRepository;

    /**
     * Controller to get all players.
     *
     * @return list of player dto.
     */
    @GetMapping
    public List<PlayerDTO> getAllPlayers() {
        return playerService.getAllPlayers().stream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * Controller to create the player.
     *
     * @param dto player dto
     * @return player dto
     */
    @PostMapping
    public PlayerDTO createPlayer(@RequestBody PlayerDTO dto) {
        Player player = fromDTO(dto);
        return toDTO(playerService.createPlayer(player));
    }

    /**
     * Controller to update the player.
     *
     * @param id player id
     * @param dto player dto
     * @return player dto
     */
    @PutMapping("/{id}")
    public PlayerDTO updatePlayer(@PathVariable Long id, @RequestBody PlayerDTO dto) {
        Player updated = playerService.updatePlayer(id, fromDTO(dto));
        return toDTO(updated);
    }

    /**
     * Controller to delete the player.
     *
     * @param id player id.
     */
    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
    }

    /**
     * Helper method to map player to player dto.
     *
     * @param player player entity
     * @return player dto
     */
    private PlayerDTO toDTO(Player player) {
        PlayerDTO dto = new PlayerDTO();
        dto.setId(player.getId());
        dto.setName(player.getName());
        dto.setSkillLevel(player.getSkillLevel());
        dto.setTeamId(player.getTeam() != null ? player.getTeam().getId() : null);
        return dto;
    }

    /**
     * Helper method to map player  from player dto.
     *
     * @param dto  player dto
     * @return player entity.
     */
    private Player fromDTO(PlayerDTO dto) {
        Team team = dto.getTeamId() != null
                ? teamRepository.findById(dto.getTeamId()).orElse(null)
                : null;
        return Player.builder()
                .id(dto.getId())
                .name(dto.getName())
                .skillLevel(dto.getSkillLevel())
                .team(team)
                .build();
    }
}
