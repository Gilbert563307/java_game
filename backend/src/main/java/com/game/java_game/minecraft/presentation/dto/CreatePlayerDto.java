package com.game.java_game.minecraft.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public class CreatePlayerDto {
    
    @NotBlank(message="Your username cannot be empty")
    private String username;
    
    public CreatePlayerDto() {
    }

    public CreatePlayerDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
