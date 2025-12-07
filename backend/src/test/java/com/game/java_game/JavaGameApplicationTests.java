package com.game.java_game;

import com.game.java_game.minecraft.domain.Cordinations;
import com.game.java_game.minecraft.domain.Game;
import com.game.java_game.minecraft.domain.Player;

import com.game.java_game.minecraft.domain.enums.Direction;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JavaGameApplicationTests {

    @Test
    void startGame() {
        Game gameOne = new Game(1L);
        gameOne.startGame(null);

        Cordinations cordinations = new Cordinations(0L, 0, 0, 0);
        Player player1 = new Player(0L, "James", 100, 10, cordinations);
        Player player2 = new Player(1L, "Doe", 100, 10, cordinations);
        gameOne.joinGame(player1);
        gameOne.joinGame(player2);

        gameOne.movePlayer(player1, Direction.RIGHT);
        gameOne.movePlayer(player2, Direction.LEFT);


        System.out.println(gameOne.getGameSession());
    }

}
