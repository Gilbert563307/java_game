package com.game.java_game.minecraft.domain.mapper;

import java.util.ArrayList;

import com.game.java_game.minecraft.domain.Coordinates;
import com.game.java_game.minecraft.domain.Game;
import com.game.java_game.minecraft.domain.Inventory;
import com.game.java_game.minecraft.domain.Player;
import com.game.java_game.minecraft.domain.dto.GameDto;
import com.game.java_game.minecraft.domain.dto.PlayerDto;
import com.game.java_game.minecraft.domain.dto.WorldMapDto;

public class GameMapper {

    public static GameDto toGameDto(Game game) {
        return new GameDto(game.getId(), game.getWorldName(), new WorldMapDto(game.getLootItems(), game.getPlayers()));
    }

    public static Player toPlayerEntity(String username) {
        return new Player(null, username, 100, 0, new Inventory(null, new ArrayList<>()), new Coordinates(null, 0, 0));
    }

    public static PlayerDto toPlayerDto(Player player) {
        return new PlayerDto(player.getId(), player.getUsername(), player.getHealth(), player.getExperience(),
                player.getInventory(), player.getCoordinates());
    }
}
