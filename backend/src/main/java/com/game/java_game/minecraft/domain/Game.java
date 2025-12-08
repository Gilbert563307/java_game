package com.game.java_game.minecraft.domain;

import java.util.List;

import com.game.java_game.minecraft.domain.enums.Direction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Game {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(targetEntity = World.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private World world;
    private boolean gameStarted;
    @OneToOne(targetEntity = Session.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private Session session;

    public Game() {

    }

    public Game(Long id, World world) {
        this.id = id;
        this.world = world;
    }

    public void startGame(List<Player> players) {
        if (this.gameStarted) {
            throw new RuntimeException("Game has already started");
        }
        this.session = new Session(null, players);
        this.gameStarted = true;
    }

    public void joinGame(Player player) {
        if (!this.gameStarted) {
            throw new RuntimeException("You cannot join the game while its not started");
        }
        this.session.join(player);
    }

    public void leaveGame(Player player) {
        if (!this.gameStarted) {
            throw new RuntimeException("You cannot leave the game while its not started");
        }
        this.session.leave(player);
    }

    public void movePlayer(Player player, Direction direction) {
        boolean isPlayerInCurrentSession = this.session.isPlayerInSession(player);
        if (!isPlayerInCurrentSession) {
            throw new RuntimeException("You cannot move a player who is not in the game");
        }
        player.move(direction);
    }

    public void pickUpItem(Player player, Item item) {
        boolean isPlayerInCurrentSession = this.session.isPlayerInSession(player);
        if (!isPlayerInCurrentSession) {
            throw new RuntimeException("You cannot move pick up an item. Because you are not in a game");
        }
        player.pickUpItem(item);
    }

    public void dropUpItem(Player player, Item item) {
        boolean isPlayerInCurrentSession = this.session.isPlayerInSession(player);
        if (!isPlayerInCurrentSession) {
            throw new RuntimeException("You cannot move pick up an item. Because you are not in a game");
        }
        player.dropItem(item);
    }

    public List<String> getPlayerList() {
        return this.session.getPlayersNames();
    }

    private World getWorld() {
        return this.world;
    }

    public String getWorldName(){
        return this.world.getName();
    }

    public Long getId(){
        return this.id;
    }

    public List<Item> getLootItems(){
        return this.world.getLootItems();
    }

    public List<Player> getPlayers(){
        return this.session.getPlayerList();
    }

    public void quitGame() {
        this.gameStarted = false;
        this.world = null;
    }

    public boolean isGameStarted() {
        return this.gameStarted;
    }
}
