package com.game.java_game.minecraft.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.game.java_game.minecraft.data.GameRepository;
import com.game.java_game.minecraft.data.PlayerRepository;
import com.game.java_game.minecraft.domain.Coordinates;
import com.game.java_game.minecraft.domain.Game;
import com.game.java_game.minecraft.domain.Inventory;
import com.game.java_game.minecraft.domain.Player;
import com.game.java_game.minecraft.domain.World;
import com.game.java_game.minecraft.domain.dto.GameDto;
import com.game.java_game.minecraft.domain.enums.Direction;
import com.game.java_game.minecraft.domain.mapper.GameMapper;
import com.game.java_game.minecraft.presentation.exception.BadRequestException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    public GameService(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    public GameDto startGame(String worldName, String playerName) {
        Player player = this.playerRepository.findByUsername(playerName);
        if (player != null) {
            throw new BadRequestException("You cannot start a game with the current player, because he is still in another game");
        }

        Game game = new Game(null, new World(null, worldName));
        List<Player> players = new ArrayList<>();

        players.add(new Player(null, playerName, 100, 0, new Inventory(null, new ArrayList<>()),
                new Coordinates(null, 0, 0)));

        game.startGame(players);
        this.gameRepository.save(game);
        return GameMapper.toGameDto(game);
    }

    public void quitGame(Long gameId) {
        Game game = this.getGameById(gameId);
        game.quitGame();
        this.gameRepository.save(game);
    }

    public void joinGame(Long gameId, Player player) {
        Game game = this.getGameById(gameId);
        game.joinGame(player);
        this.gameRepository.save(game);
    }

    public void leaveGame(Long gameId, Player player) {
        Game game = this.getGameById(gameId);
        game.leaveGame(player);
        this.gameRepository.save(game);
    }

    public void movePlayer(Long gameId, Player player, Direction direction) {
        Game game = this.getGameById(gameId);
        game.movePlayer(player, direction);
        this.gameRepository.save(game);
    }

    public GameDto getGameStatus(Long gameId) {
        Game game = this.getGameById(gameId);
        return GameMapper.toGameDto(game);
    }

    private Game getGameById(Long gameId) {
        return this.gameRepository.findById(gameId).orElseThrow(() -> new BadRequestException("Could not find game"));
    }

    public Player getPlayerByUserName(String username) {
        Player player = this.playerRepository.findByUsername(username);
        if (player == null) {
            throw new BadRequestException("Could not find player");
        }
        return player;
    }

}
