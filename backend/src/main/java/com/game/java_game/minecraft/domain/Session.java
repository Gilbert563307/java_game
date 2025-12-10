package com.game.java_game.minecraft.domain;


import java.util.Collections;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Session {
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(targetEntity = Player.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> playerList;

    
    public Session() {
    }


    public Session(Long id, List<Player> playerList) {
        this.id = id;
        this.playerList = playerList;
    }

    public void join(Player player) {
        if (!this.playerList.contains(player)) {
            this.playerList.add(player);
        }
    }

    public void leave(Player player) {
        if (!this.playerList.contains(player)) {
            this.playerList.remove(player);
        }
    }

    public Player getPlayerById(Long playerId) {
        return this.playerList
                .stream()
                .filter(p -> p.getId().equals(playerId))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Player is not in this game"));
    }

    public boolean isPlayerInSession(Player player) {
        return this.playerList.contains(player);
    }

    public List<String> getPlayersNames() {
        return this.playerList.stream().map(Player::getUsername).toList();
    }

    protected  List<Player> getPlayerList(){
        return Collections.unmodifiableList(this.playerList);
    }
}
