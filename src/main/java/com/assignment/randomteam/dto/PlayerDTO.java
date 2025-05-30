package com.assignment.randomteam.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlayerDTO {
    private Long id;
    private String name;
    private int skillLevel;
    private Long teamId; // nullable
}
