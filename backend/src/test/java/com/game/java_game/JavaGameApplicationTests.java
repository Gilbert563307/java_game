package com.game.java_game;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.game.java_game.minecraft.domain.Coordinates;
import com.game.java_game.minecraft.domain.Game;
import com.game.java_game.minecraft.domain.Inventory;
import com.game.java_game.minecraft.domain.Item;
import com.game.java_game.minecraft.domain.Player;
import com.game.java_game.minecraft.domain.World;
import com.game.java_game.minecraft.domain.enums.Direction;

@SpringBootTest
class JavaGameApplicationTests {

    @Test
    void startGame() {
        Game gameOne = new Game(null, new World(null, "New world"));
        List<Player> players = new ArrayList<>();

        players.add(
                new Player(null, "Harvy_1", 100, 0, new Inventory(null, new ArrayList<>()), new Coordinates(null, 10, 5)));
        gameOne.startGame(players);

    
        Player playerOne = new Player(1L, "James_712", 100, 0, new Inventory(1L, new ArrayList<>()), new Coordinates(1L, 0, 0));
        Player playerTwo = new Player(2L, "Thomas_771", 100, 0, new Inventory(2L, new ArrayList<>()), new Coordinates(2L, 0, 0));

        gameOne.joinGame(playerOne);

        assertTrue(gameOne.isGameStarted(), "Game is not started");
        assertEquals(2, gameOne.getPlayerList().size());

        // Check that moving a player not in the game throws the correct exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            gameOne.movePlayer(playerTwo, Direction.UP);
        });
        assertEquals("You cannot move a player who is not in the game", exception.getMessage());

        gameOne.joinGame(playerTwo);
        assertEquals(3, gameOne.getPlayerList().size());

        gameOne.movePlayer(playerTwo, Direction.UP);

        gameOne.movePlayer(playerOne, Direction.LEFT);
        gameOne.movePlayer(playerOne, Direction.LEFT);

     
        playerOne.pickUpItem(new Item(null, "Magic brush", new Coordinates(null, 0, 0)));
        playerTwo.pickUpItem(new Item(null, "Magic brush", new Coordinates(null, 0, 0)));

        assertEquals(1, playerOne.getInventory().getInventoryItems().size());
        assertEquals(0, playerTwo.getInventory().getInventoryItems().size());
    }

}
