package com.game.java_game;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.game.java_game.minecraft.domain.Coordinates;
import com.game.java_game.minecraft.domain.Game;
import com.game.java_game.minecraft.domain.Inventory;
import com.game.java_game.minecraft.domain.Player;
import com.game.java_game.minecraft.domain.enums.Direction;

@SpringBootTest
class JavaGameApplicationTests {

    @Test
    void startGame() {
        Game gameOne = new Game(1L);
        gameOne.startGame(null);

        Inventory inventory = new Inventory(1L, new ArrayList<>());
       
        Player playerOne = new Player(1L, "James_712", 100, 0, inventory, new Coordinates(1L, 0, 0));
        Player playerTwo = new Player(2L, "Thomas_771", 100, 0, inventory, new Coordinates(2L, 0, 0));

        gameOne.joinGame(playerOne);

        assertTrue(gameOne.isGameStarted(), "Game is not started");
        assertEquals(gameOne.getPlayerList().size(), 1);

        // Check that moving a player not in the game throws the correct exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            gameOne.movePlayer(playerTwo, Direction.UP);
        });
        assertEquals("You cannot move a player who is not in the game", exception.getMessage());

        gameOne.joinGame(playerTwo);
        assertEquals(gameOne.getPlayerList().size(), 2);

        gameOne.movePlayer(playerTwo, Direction.UP);
        
        gameOne.movePlayer(playerOne, Direction.LEFT);
        gameOne.movePlayer(playerOne, Direction.LEFT);
        gameOne.movePlayer(playerOne, Direction.LEFT);


        //todo make pick up item method

        System.out.println(playerTwo.getCoordinates());
        System.out.println(playerOne.getCoordinates());
    }

}
