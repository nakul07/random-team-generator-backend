package com.assignment.randomteam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GeneratedTeamDTO {
    private String teamName;
    private List<PlayerDTO> players;
}
