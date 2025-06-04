package com.assignment.randomteam.controller;

import com.assignment.randomteam.dto.TeamDTO;
import com.assignment.randomteam.entity.Team;
import com.assignment.randomteam.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
@CrossOrigin
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    /**
     * Controller to get all teams.
     *
     * @return list of team DTO
     */
    @GetMapping
    public List<TeamDTO> getAllTeams() {
        return teamService.getAllTeams().stream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * Controller to create the team.
     *
     * @param dto team dto
     * @return team dto
     */
    @PostMapping
    public TeamDTO createTeam(@RequestBody TeamDTO dto) {
        return toDTO(teamService.createTeam(fromDTO(dto)));
    }

    /**
     * Controller to update the team.
     *
     * @param id team id
     * @param dto team dto
     * @return team dto
     */
    @PutMapping("/{id}")
    public TeamDTO updateTeam(@PathVariable Long id, @RequestBody TeamDTO dto) {
        return toDTO(teamService.updateTeam(id, fromDTO(dto)));
    }

    /**
     * Controller to delete the team.
     *
     * @param id team id.
     */
    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
    }

    /**
     * Helper method to map team to team dto.
     *
     * @param team team entity
     * @return team dto
     */
    private TeamDTO toDTO(Team team) {
        TeamDTO dto = new TeamDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        return dto;
    }

    /**
     * Helper method to map team from team dto.
     *
     * @param dto team dto
     * @return team
     */
    private Team fromDTO(TeamDTO dto) {
        return Team.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
