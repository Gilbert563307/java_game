package com.game.java_game.minecraft.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record JoinGameDto(@NotBlank(message = "Your username cannot be empty") String username) {

}
