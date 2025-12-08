package com.game.java_game.minecraft.domain;

import java.util.List;

import com.game.java_game.minecraft.domain.enums.Direction;

public class Game {
    private Long id;
    private World world;
    private boolean gameStarted;

    public Game() {

    }

    public Game(Long id) {
        this.id = id;
        this.world = null;
        this.gameStarted = false;
    }

    public void startGame(World world) {
        if (this.gameStarted) {
            throw new RuntimeException("Game has already started");
        }

        if (world == null) {
            this.world = new World(null, "Your world");
        } else {
            this.world = world;
        }
        this.gameStarted = true;
    }

    public void joinGame(Player player) {
        if (!this.gameStarted) {
            throw new RuntimeException("You cannot join the game while its not started");
        }
        this.world.join(player);
    }

    public void leaveGame(Player player) {
        if (!this.gameStarted) {
            throw new RuntimeException("You cannot leave the game while its not started");
        }
        this.world.leave(player);
    }

    public void movePlayer(Player player, Direction direction) {
        boolean isPlayerInCurrentSession = this.world.isPlayerInWorld(player);
        if (!isPlayerInCurrentSession) {
            throw new RuntimeException("You cannot move a player who is not in the game");
        }
        player.move(direction);
    }

    public void pickUpItem(Player player, Item item) {
        boolean isPlayerInCurrentSession = this.world.isPlayerInWorld(player);
        if (!isPlayerInCurrentSession) {
            throw new RuntimeException("You cannot move pick up an item. Because you are not in a game");
        }
        player.pickUpItem(item);
    }

    public void dropUpItem(Player player, Item item) {
        boolean isPlayerInCurrentSession = this.world.isPlayerInWorld(player);
        if (!isPlayerInCurrentSession) {
            throw new RuntimeException("You cannot move pick up an item. Because you are not in a game");
        }
        player.dropItem(item);
    }

    public List<String> getPlayerList() {
        return this.world.getPlayersNames();
    }

    public World getWorld() {
        return this.world;
    }

    public void quitGme() {
        this.gameStarted = false;
        this.world = null;
    }

    public boolean isGameStarted() {
        return this.gameStarted;
    }
}
