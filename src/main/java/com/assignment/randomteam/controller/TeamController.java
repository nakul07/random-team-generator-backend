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

    @GetMapping
    public List<TeamDTO> getAllTeams() {
        return teamService.getAllTeams().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @PostMapping
    public TeamDTO createTeam(@RequestBody TeamDTO dto) {
        return toDTO(teamService.createTeam(fromDTO(dto)));
    }

    @PutMapping("/{id}")
    public TeamDTO updateTeam(@PathVariable Long id, @RequestBody TeamDTO dto) {
        return toDTO(teamService.updateTeam(id, fromDTO(dto)));
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
    }

    private TeamDTO toDTO(Team team) {
        TeamDTO dto = new TeamDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        return dto;
    }

    private Team fromDTO(TeamDTO dto) {
        return Team.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
