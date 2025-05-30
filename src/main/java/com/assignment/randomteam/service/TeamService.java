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

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team updateTeam(Long id, Team updatedTeam) {
        Team existing = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));
        existing.setName(updatedTeam.getName());
        return teamRepository.save(existing);
    }

    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new EntityNotFoundException("Team not found");
        }
        teamRepository.deleteById(id);
    }
}
