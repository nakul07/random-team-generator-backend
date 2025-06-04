package com.assignment.randomteam.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamGenerationRequest {
    @NotBlank
    private String title;

    @Min(value = 2, message = "Must have at least 2 teams")
    private int numberOfTeams;

    @Min(value = 5, message = "Must have at least 5 players")
    private int numberOfPlayers;
}
