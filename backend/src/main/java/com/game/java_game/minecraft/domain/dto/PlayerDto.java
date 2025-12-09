package com.game.java_game.minecraft.domain.dto;

import com.game.java_game.minecraft.domain.Coordinates;
import com.game.java_game.minecraft.domain.Inventory;

public record PlayerDto(Long id, String username, int health, int experience, Inventory inventory,
        Coordinates coordinates) {
}
