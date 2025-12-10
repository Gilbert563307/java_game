package com.game.java_game.minecraft.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.java_game.minecraft.application.service.GameService;
import com.game.java_game.minecraft.application.service.PlayerService;
import com.game.java_game.minecraft.domain.Player;
import com.game.java_game.minecraft.domain.dto.PlayerDto;
import com.game.java_game.minecraft.presentation.dto.CreatePlayerDto;
import com.game.java_game.security.UserProfile;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/player")
public class PlayerController {

    private final GameService gameService;
    private final PlayerService playerService;

    public PlayerController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<PlayerDto> createPlayer(@RequestBody @Valid CreatePlayerDto createPlayerDto) {
        PlayerDto playerDto = this.playerService.createPlayer(createPlayerDto);
        return ResponseEntity.ok(playerDto);
    }

    @PostMapping("/{gameId}/game")
    public ResponseEntity<Void> join(UserProfile profile, @PathVariable Long gameId) {
        Player player = this.gameService.getPlayerByUserName(profile.getUsername());
        this.gameService.joinGame(gameId, player);
        return ResponseEntity.created(null).build();
    }

    @PutMapping("/{gameId}/game")
    public ResponseEntity<Void> leave(UserProfile profile, @PathVariable Long gameId) {
        Player player = this.gameService.getPlayerByUserName(profile.getUsername());
        this.gameService.leaveGame(gameId, player);
        return ResponseEntity.ok(null);
    }

}
