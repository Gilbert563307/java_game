package com.game.java_game.minecraft.application.service;


import com.game.java_game.minecraft.data.PlayerRepository;
import com.game.java_game.minecraft.domain.Player;
import com.game.java_game.minecraft.domain.dto.PlayerDto;
import com.game.java_game.minecraft.domain.mapper.GameMapper;
import com.game.java_game.minecraft.presentation.dto.CreatePlayerDto;


public class PlayerService {

    private PlayerRepository playerRepository;

    public PlayerDto createPlayer(CreatePlayerDto createPlayerDto) {
        Player player = GameMapper.toPlayerEntity(createPlayerDto.getUsername());
        this.playerRepository.save(player);
        return GameMapper.toPlayerDto(player);
    }
    
}
