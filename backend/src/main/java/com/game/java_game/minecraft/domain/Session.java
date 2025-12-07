package com.game.java_game.minecraft.domain;

import com.game.java_game.minecraft.domain.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private Long id;
    private List<Player> playerList;

    public Session() {
    }

    public Session(Long id) {
        this.id = id;
        this.playerList = new ArrayList<Player>();
    }

    public void join(Player player){
        if(!this.playerList.contains(player)){
            this.playerList.add(player);
        }
    }

    public void leave(Player player){
        if(!this.playerList.contains(player)){
            this.playerList.remove(player);
        }
    }

    public boolean isPlayerInCurrentSession(Player player){
        return this.playerList.contains(player);
    }

    public List<String> getPlayers(){
        return this.playerList.stream().map(Player::getUsername).toList();
    }

    public void movePlayer(Player player, Direction direction) {
        for( Player listPlayer : this.playerList){
            if(listPlayer.getId().equals(player.getId())){
                player.updateLocation(direction);
                return;
            }
        }

    }
}
