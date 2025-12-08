package com.game.java_game.minecraft.application.service;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import com.game.java_game.minecraft.data.GameRepository;
import com.game.java_game.minecraft.domain.Game;
import com.game.java_game.minecraft.domain.Player;
import com.game.java_game.minecraft.domain.World;
import com.game.java_game.minecraft.domain.dto.WorldMapDto;
import com.game.java_game.minecraft.domain.enums.Direction;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void startGame(Long gameId) throws BadRequestException {
        Game game = this.getGameById(gameId);
        game.startGame(null);
        this.gameRepository.save(game);
    }

    public void quitGame(Long gameId) throws BadRequestException {
        Game game = this.getGameById(gameId);
        game.quitGme();
        this.gameRepository.save(game);
    }

    public void joinGame(Long gameId, Player player) throws BadRequestException {
        Game game = this.getGameById(gameId);
        game.joinGame(player);
        this.gameRepository.save(game);
    }

    public void leaveGame(Long gameId, Player player) throws BadRequestException {
        Game game = this.getGameById(gameId);
        game.leaveGame(player);
        this.gameRepository.save(game);
    }

    public void movePlayer(Long gameId, Player player, Direction direction) throws BadRequestException {
        Game game = this.getGameById(gameId);
        game.movePlayer(player, direction);
        this.gameRepository.save(game);
    }

    public WorldMapDto getWorld(Long gameId) throws BadRequestException {
        Game game = this.getGameById(gameId);
        World world = game.getWorld();
        return new WorldMapDto(world.getLootItems(), world.getPlayerList());
    }

    private Game getGameById(Long gameId) throws BadRequestException {
        Game game = this.gameRepository.findById(gameId)
                .orElseThrow(() -> new BadRequestException("Game niet gevonden"));
        return game;
    }

}
