package com.assignment.randomteam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TeamGenerationResponse {
    private UUID publicId;
    private List<GeneratedTeamDTO> teams;
}
