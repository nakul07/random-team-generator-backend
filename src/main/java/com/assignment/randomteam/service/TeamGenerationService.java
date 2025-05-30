package com.assignment.randomteam.service;

import com.assignment.randomteam.dto.GeneratedTeamDTO;
import com.assignment.randomteam.dto.PlayerDTO;
import com.assignment.randomteam.entity.GeneratedTeamPlayer;
import com.assignment.randomteam.entity.Player;
import com.assignment.randomteam.entity.TeamGenerationSession;
import com.assignment.randomteam.repository.GeneratedTeamPlayerRepository;
import com.assignment.randomteam.repository.PlayerRepository;
import com.assignment.randomteam.repository.TeamGenerationSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamGenerationService {

    private final PlayerRepository playerRepository;
    private final GeneratedTeamPlayerRepository generatedTeamPlayerRepository;
    private final TeamGenerationSessionRepository sessionRepository;

    public List<GeneratedTeamDTO> generateBalancedTeams(String title, int numberOfTeams) {
        List<Player> players = playerRepository.findAll();
        players.sort(Comparator.comparingInt(Player::getSkillLevel).reversed());

        List<GeneratedTeamDTO> teams = new ArrayList<>();
        for (int i = 0; i < numberOfTeams; i++) {
            teams.add(new GeneratedTeamDTO("Team " + (i + 1), new ArrayList<>()));
        }

        // Create session with public ID
        TeamGenerationSession session = TeamGenerationSession.builder()
                .title(title)
                .publicId(UUID.randomUUID())
                .build();
        session = sessionRepository.save(session);

        List<GeneratedTeamPlayer> savedPlayers = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            int teamIndex = i % numberOfTeams;
            Player p = players.get(i);

            PlayerDTO dto = new PlayerDTO(p.getId(), p.getName(), p.getSkillLevel(), p.getTeam() != null ? p.getTeam().getId() : null);
            teams.get(teamIndex).getPlayers().add(dto);

            GeneratedTeamPlayer generatedPlayer = GeneratedTeamPlayer.builder()
                    .playerName(p.getName())
                    .skillLevel(p.getSkillLevel())
                    .teamName(teams.get(teamIndex).getTeamName())
                    .session(session)
                    .build();

            savedPlayers.add(generatedPlayer);
        }

        generatedTeamPlayerRepository.saveAll(savedPlayers);
        return teams;
    }
}


