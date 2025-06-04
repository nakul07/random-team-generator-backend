package com.assignment.randomteam.service;

import com.assignment.randomteam.entity.Team;
import com.assignment.randomteam.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    /**
     * Method to create the team.
     *
     * @param team team entity
     * @return team entity
     */
    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    /**
     * Method to get all teams.
     *
     * @return List of teams
     */
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    /**
     * Method to update the team.
     *
     * @param id team id
     * @param updatedTeam team entity
     * @return updated team entity
     */
    public Team updateTeam(Long id, Team updatedTeam) {
        Team existing = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));
        existing.setName(updatedTeam.getName());
        return teamRepository.save(existing);
    }

    /**
     * Method to delete a team.
     *
     * @param id team id
     */
    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new EntityNotFoundException("Team not found");
        }
        teamRepository.deleteById(id);
    }
}
