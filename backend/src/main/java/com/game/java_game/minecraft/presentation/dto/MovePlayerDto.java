package com.game.java_game.minecraft.presentation.dto;

import com.game.java_game.minecraft.domain.enums.Direction;
import com.game.java_game.minecraft.presentation.exception.BadRequestException;

import jakarta.validation.constraints.NotNull;

public class MovePlayerDto {

    @NotNull(message = "Your direction cannot be null")
    private Direction direction;

    public MovePlayerDto() {
    }

    public MovePlayerDto(Direction direction) {
        if (direction == null) {
            throw new BadRequestException("Direction cannot be null");
        }
        this.direction = direction;
    }

    public Direction getDirection() {
        return this.direction;
    }

}
