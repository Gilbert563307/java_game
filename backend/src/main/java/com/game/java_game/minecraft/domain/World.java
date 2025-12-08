package com.game.java_game.minecraft.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    private Long id;
    private String name;
    private List<Item> lootItems;
    private List<Player> playerList;

    // World size 
    public static final int WORLD_WIDTH = 100;
    public static final int WORLD_HEIGHT = 100;

    public World() {

    }

    public World(Long id, String name) {
        this.id = id;
        this.name = name;
        this.lootItems = new ArrayList<>();
        this.playerList = new ArrayList<>();
        this.loadRandomItemsInGame();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public boolean isPlayerInWorld(Player player) {
        return this.playerList.contains(player);
    }

    public List<String> getPlayersNames() {
        return this.playerList.stream().map(Player::getUsername).toList();
    }


    private void loadRandomItemsInGame() {
        Random random = new Random();

        lootItems.add(createItem(0L, "Apple", random));
        lootItems.add(createItem(1L, "Wood", random));
        lootItems.add(createItem(2L, "Stone", random));
        lootItems.add(createItem(3L, "Iron Ore", random));
        lootItems.add(createItem(4L, "Gold Ore", random));
        lootItems.add(createItem(5L, "Diamond", random));
        lootItems.add(createItem(6L, "Stick", random));
        lootItems.add(createItem(7L, "Torch", random));
        lootItems.add(createItem(8L, "Bread", random));
        lootItems.add(createItem(9L, "Leather", random));
        lootItems.add(createItem(10L, "Coal", random));
        lootItems.add(createItem(11L, "Sword", random));
        lootItems.add(createItem(12L, "Pickaxe", random));
        lootItems.add(createItem(13L, "Bow", random));
        lootItems.add(createItem(14L, "Arrow", random));
        lootItems.add(new Item(99L, "Magic brush", new Coordinates(99L, 2, 2)));
    }

    private Item createItem(Long itemId, String name, Random random) {
        int x = random.nextInt(WORLD_WIDTH);
        int y = random.nextInt(WORLD_HEIGHT);

        Coordinates coordinates = new Coordinates(itemId, x, y);
        return new Item(itemId, name, coordinates);
    }

    public List<Item> getLootItems() {
        return lootItems;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

  
}
