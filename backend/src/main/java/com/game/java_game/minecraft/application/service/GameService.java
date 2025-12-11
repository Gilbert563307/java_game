package com.game.java_game.minecraft.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.game.java_game.minecraft.data.GameRepository;
import com.game.java_game.minecraft.domain.Game;
import com.game.java_game.minecraft.domain.Player;
import com.game.java_game.minecraft.domain.World;
import com.game.java_game.minecraft.domain.dto.GameDto;
import com.game.java_game.minecraft.domain.dto.PlayerDto;
import com.game.java_game.minecraft.domain.enums.Direction;
import com.game.java_game.minecraft.domain.mapper.GameMapper;
import com.game.java_game.minecraft.domain.exception.InvalidGameOperationException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameDto startGame(String worldName, String playerName) {
        Game game = new Game(null, new World(null, worldName));
        List<Player> players = new ArrayList<>();
        players.add(GameMapper.toPlayerEntity(playerName));
        game.startGame(players);
        this.gameRepository.save(game);
        return GameMapper.toGameDto(game);
    }

    public void quitGame(Long gameId) {
        Game game = this.getGameById(gameId);
        this.gameRepository.delete(game);
    }

    public void joinGame(Long gameId, String username) {
        Game game = this.getGameById(gameId);
        Player player = GameMapper.toPlayerEntity(username);
        game.joinGame(player);
        this.gameRepository.save(game);
    }

    public void leaveGame(Long gameId, Long playerId) {
        Game game = this.getGameById(gameId);
        Player player = game.getPlayerById(playerId);
        game.leaveGame(player);
        this.gameRepository.save(game);
    }

    public PlayerDto movePlayer(Long gameId, Long playerId, Direction direction) {
        Game game = this.getGameById(gameId);
        Player player = game.getPlayerById(playerId);
        game.movePlayer(player, direction);
        this.gameRepository.save(game);
        return GameMapper.toPlayerDto(player);
    }

    public GameDto getGameStatus(Long gameId) {
        Game game = this.getGameById(gameId);
        return GameMapper.toGameDto(game);
    }

    public PlayerDto getPlayerInGame(Long gameId, Long playerId) {
        Game game = this.getGameById(gameId);
        Player player = game.getPlayerById(playerId);
        return GameMapper.toPlayerDto(player);
    }

    private Game getGameById(Long gameId) {
        return this.gameRepository.findById(gameId).orElseThrow(() -> new InvalidGameOperationException("Could not find game"));
    }
}
