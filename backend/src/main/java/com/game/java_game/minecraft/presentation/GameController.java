package com.game.java_game.minecraft.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.java_game.minecraft.application.service.GameService;
import com.game.java_game.minecraft.domain.dto.GameDto;
import com.game.java_game.minecraft.presentation.dto.CreateGameDto;
import com.game.java_game.security.UserProfile;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameDto> getGame(@PathVariable Long gameId) {
        GameDto gameDto = this.gameService.getGameStatus(gameId);
        return ResponseEntity.ok(gameDto);
    }

    @PostMapping
    public ResponseEntity<GameDto> create(UserProfile profile, @Valid @RequestBody CreateGameDto createGameDto) {
        GameDto gameDto = this.gameService.startGame(createGameDto.getName(), profile.getUsername());
        return ResponseEntity.ok(gameDto);
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> quitGame(@PathVariable Long gameId) {
        this.gameService.quitGame(gameId);
        return ResponseEntity.ok(null);
    }

}
