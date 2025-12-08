package com.game.java_game.minecraft.domain.mapper;

import com.game.java_game.minecraft.domain.Game;
import com.game.java_game.minecraft.domain.dto.GameDto;
import com.game.java_game.minecraft.domain.dto.WorldMapDto;

public class GameMapper {

    public static GameDto toGameDto(Game game) {
        return new GameDto(game.getId(), game.getWorldName(), new WorldMapDto(game.getLootItems(), game.getPlayers()));
    }
}
