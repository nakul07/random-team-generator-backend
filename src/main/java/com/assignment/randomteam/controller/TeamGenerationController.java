package com.assignment.randomteam.controller;

import com.assignment.randomteam.dto.GeneratedTeamDTO;
import com.assignment.randomteam.dto.PlayerDTO;
import com.assignment.randomteam.dto.TeamGenerationRequest;
import com.assignment.randomteam.dto.TeamGenerationResponse;
import com.assignment.randomteam.entity.GeneratedTeamPlayer;
import com.assignment.randomteam.entity.TeamGenerationSession;
import com.assignment.randomteam.repository.TeamGenerationSessionRepository;
import com.assignment.randomteam.service.TeamGenerationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/team-generation")
@CrossOrigin
@RequiredArgsConstructor
public class TeamGenerationController {

    private final TeamGenerationService teamGenerationService;
    private final TeamGenerationSessionRepository sessionRepository;

    @PostMapping
    public TeamGenerationResponse generateTeams(@Valid @RequestBody TeamGenerationRequest request) {
        return teamGenerationService.generateBalancedTeams(request.getTitle(), request.getNumberOfTeams(), request.getNumberOfPlayers());
    }


    @GetMapping("/public/{uuid}")
    public List<GeneratedTeamDTO> viewGeneratedTeams(@PathVariable UUID uuid) {
        TeamGenerationSession session = sessionRepository.findByPublicId(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));

        List<GeneratedTeamPlayer> players = session.getPlayers();

        Map<String, List<PlayerDTO>> teamMap = new LinkedHashMap<>();

        for (GeneratedTeamPlayer p : players) {
            teamMap.putIfAbsent(p.getTeamName(), new ArrayList<>());
            PlayerDTO dto = new PlayerDTO();
            dto.setName(p.getPlayerName());
            dto.setSkillLevel(p.getSkillLevel());
            teamMap.get(p.getTeamName()).add(dto);
        }

        List<GeneratedTeamDTO> result = new ArrayList<>();
        teamMap.forEach((teamName, members) -> result.add(new GeneratedTeamDTO(teamName, members)));

        return result;
    }

}
