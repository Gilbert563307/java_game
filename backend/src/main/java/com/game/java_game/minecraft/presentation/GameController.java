package com.game.java_game.minecraft.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.java_game.minecraft.application.service.GameService;
import com.game.java_game.minecraft.domain.dto.GameDto;
import com.game.java_game.minecraft.domain.dto.PlayerDto;
import com.game.java_game.minecraft.domain.dto.StartGameDto;
import com.game.java_game.minecraft.presentation.dto.JoinGameDto;
import com.game.java_game.minecraft.presentation.dto.MovePlayerDto;
import com.game.java_game.minecraft.presentation.dto.MovePlayerToDestinationDto;
import com.game.java_game.minecraft.presentation.dto.StartAndCreateGameDto;

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
    public ResponseEntity<StartGameDto> startGame(@Valid @RequestBody StartAndCreateGameDto payload) {
        StartGameDto startGameDto = this.gameService.startGame(payload.worldName(), payload.username());
        return ResponseEntity.ok(startGameDto);
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

      // move a player in the game
    @PatchMapping("/{gameId}/players/{playerId}/coordinates")
    public ResponseEntity<PlayerDto> movePlayerToLocation(@PathVariable Long gameId, @PathVariable Long playerId,
            @Valid @RequestBody MovePlayerToDestinationDto movePlayerToDestinationDto) {
      
        PlayerDto playerDto = this.gameService.movePlayerToDestination(gameId, playerId, movePlayerToDestinationDto.getX(), movePlayerToDestinationDto.getY());
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
