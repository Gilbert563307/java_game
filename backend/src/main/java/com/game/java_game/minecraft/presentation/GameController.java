package com.game.java_game.minecraft.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.game.java_game.minecraft.application.service.GameService;
import com.game.java_game.minecraft.domain.dto.GameDto;
import com.game.java_game.minecraft.domain.dto.PlayerDto;
import com.game.java_game.minecraft.presentation.dto.JoinGameDto;
import com.game.java_game.minecraft.presentation.dto.MovePlayerDto;
import com.game.java_game.minecraft.presentation.dto.StartGameDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/game")
@CrossOrigin(origins = "http://localhost:5173/")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // get the player in current game session
    @GetMapping("/{gameId}/player/{playerId}")
    public ResponseEntity<PlayerDto> getGamePlayerDetails(@PathVariable Long gameId, @PathVariable Long playerId) {
        PlayerDto playerDto = this.gameService.getPlayerInGame(gameId, playerId);
        return ResponseEntity.ok(playerDto);
    }

    // get current game status
    @GetMapping("/{gameId}")
    public ResponseEntity<GameDto> getGame(@PathVariable Long gameId) {
        GameDto gameDto = this.gameService.getGameStatus(gameId);
        return ResponseEntity.ok(gameDto);
    }

    // create a game & start a game
    @PostMapping
    public ResponseEntity<GameDto> startGame(@Valid @RequestBody StartGameDto startGameDto) {
        GameDto gameDto = this.gameService.startGame(startGameDto.worldName(), startGameDto.username());
        return ResponseEntity.ok(gameDto);
    }

    // quit a game
    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> quitGame(@PathVariable Long gameId) {
        this.gameService.quitGame(gameId);
        return ResponseEntity.ok(null);
    }

    // move a player in the game
    @PatchMapping("/{gameId}/players/{playerId}/position")
    public ResponseEntity<PlayerDto> movePlayer(@PathVariable Long gameId, @PathVariable Long playerId,
            @Valid @RequestBody MovePlayerDto movePlayerDto) {
        PlayerDto playerDto = this.gameService.movePlayer(gameId, playerId, movePlayerDto.getDirection());
        return ResponseEntity.ok(playerDto);
    }

    //join the game 
    @PostMapping("/{gameId}/players")
    public ResponseEntity<Void> join(@Valid @RequestBody JoinGameDto joinGameDto, @PathVariable Long gameId) {
        this.gameService.joinGame(gameId, joinGameDto.username());
        return ResponseEntity.created(null).build();
    }

    //leave the game
    @DeleteMapping("/{gameId}/players/{playerId}")
    public ResponseEntity<Void> leave(@PathVariable Long gameId,  @PathVariable Long playerId) {
        this.gameService.leaveGame(gameId, playerId);
        return ResponseEntity.ok(null);
    }

}
