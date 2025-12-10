package com.game.java_game.minecraft.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record StartGameDto(
    @NotBlank(message = "Your username cannot be empty") String username,
    @NotBlank(message="Your world name cannot be empty") String worldName
) {

}
