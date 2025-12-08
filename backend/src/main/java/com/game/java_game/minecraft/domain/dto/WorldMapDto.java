package com.game.java_game.minecraft.domain.dto;

import java.util.List;

import com.game.java_game.minecraft.domain.Item;
import com.game.java_game.minecraft.domain.Player;

public class WorldMapDto {
    private List<Item> lootItems;
    private List<Player> playerList;

    public WorldMapDto() {
    }

    public WorldMapDto(List<Item> lootItems, List<Player> playerList) {
        this.lootItems = lootItems;
        this.playerList = playerList;
    }

    public List<Item> getLootItems() {
        return lootItems;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

}
