package com.assignment.randomteam.service;

import com.assignment.randomteam.dto.GeneratedTeamDTO;
import com.assignment.randomteam.dto.PlayerDTO;
import com.assignment.randomteam.dto.TeamGenerationResponse;
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

    public TeamGenerationResponse generateBalancedTeams(String title, int numberOfTeams, int numberOfPlayersPerTeam) {
        List<Player> allPlayers = playerRepository.findAll();
        allPlayers.sort(Comparator.comparingInt(Player::getSkillLevel).reversed()); // Sort by skill level descending

        int totalPlayersRequired = numberOfTeams * numberOfPlayersPerTeam;

        // Check for insufficient players
        if (allPlayers.size() < totalPlayersRequired) {
            throw new IllegalArgumentException("Insufficient players in the database to form " + numberOfTeams +
                    " teams with " + numberOfPlayersPerTeam + " players per team. " +
                    "Required: " + totalPlayersRequired + ", Available: " + allPlayers.size());
        }

        // Trim the players list to exactly the number needed
        List<Player> playersToDistribute = allPlayers.subList(0, totalPlayersRequired);

        List<GeneratedTeamDTO> teams = new ArrayList<>();
        for (int i = 0; i < numberOfTeams; i++) {
            teams.add(new GeneratedTeamDTO("Team " + (i + 1), new ArrayList<>()));
        }

        TeamGenerationSession session = TeamGenerationSession.builder()
                .title(title)
                .publicId(UUID.randomUUID())
                .build();
        session = sessionRepository.save(session);

        List<GeneratedTeamPlayer> savedPlayers = new ArrayList<>();

        // Distribute players by filling team by team, ensuring skill balance

        int playerIndex = 0;

        for (int i = 0; i < totalPlayersRequired; i++) {
            Player p = playersToDistribute.get(playerIndex);

            int teamIndex;
            if ((playerIndex / numberOfTeams) % 2 == 0) {
                // Distribute players normally (0, 1, 2, ..., N-1 teams)
                teamIndex = playerIndex % numberOfTeams;
            } else {
                // Distribute players in reverse order (N-1, ..., 2, 1, 0 teams)
                teamIndex = numberOfTeams - 1 - (playerIndex % numberOfTeams);
            }

            teams.get(teamIndex).getPlayers().add(
                    new PlayerDTO(p.getId(), p.getName(), p.getSkillLevel(), p.getTeam() != null ? p.getTeam().getId() : null)
            );

            savedPlayers.add(
                    GeneratedTeamPlayer.builder()
                            .playerName(p.getName())
                            .skillLevel(p.getSkillLevel())
                            .teamName(teams.get(teamIndex).getTeamName())
                            .session(session)
                            .build()
            );

            playerIndex++;
        }

        generatedTeamPlayerRepository.saveAll(savedPlayers);
        return new TeamGenerationResponse(session.getPublicId(), teams);
    }
}


    