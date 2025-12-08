package com.game.java_game.minecraft.presentation.dto;

import jakarta.validation.constraints.NotNull;

public class CreateGameDto {

    @NotNull(message="The name of your world cannot be empty")
    private String name;

    public CreateGameDto() {
    }

    public CreateGameDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
