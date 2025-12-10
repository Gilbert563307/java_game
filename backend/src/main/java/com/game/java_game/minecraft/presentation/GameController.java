package com.game.java_game.minecraft.presentation;

import com.game.java_game.minecraft.domain.dto.PlayerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //get the player in current game session
    @GetMapping("/{gameId}/player/{playerId}")
    public ResponseEntity<PlayerDto> getInGamePlayer(@PathVariable Long gameId, @PathVariable Long playerId){
        PlayerDto playerDto = this.gameService.getPlayerInGame(gameId, playerId);
        return ResponseEntity.ok(playerDto);
    }

    //get current game status
    @GetMapping("/{gameId}")
    public ResponseEntity<GameDto> getGame(@PathVariable Long gameId) {
        GameDto gameDto = this.gameService.getGameStatus(gameId);
        return ResponseEntity.ok(gameDto);
    }

    //create a game & start a game
    @PostMapping
    public ResponseEntity<GameDto> startGame(UserProfile profile, @Valid @RequestBody CreateGameDto createGameDto) {
        GameDto gameDto = this.gameService.startGame(createGameDto.getName(), profile.getUsername());
        return ResponseEntity.ok(gameDto);
    }

    //quit a game
    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> quitGame(@PathVariable Long gameId) {
        this.gameService.quitGame(gameId);
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{gameId}/players/{playerId}/position")
    public  ResponseEntity<PlayerDto> movePlayer(@PathVariable){

    }



}
