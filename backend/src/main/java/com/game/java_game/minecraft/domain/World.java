package com.game.java_game.minecraft.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class World {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(targetEntity = Item.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> lootItems;

    // World size
    public static final int WORLD_WIDTH = 1000;
    public static final int WORLD_HEIGHT = 600;

    public World() {

    }

    public World(Long id, String name) {
        this.id = id;
        this.name = name;
        this.lootItems = new ArrayList<>();

        this.loadRandomItemsInGame();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Item> getLootItems() {
        return Collections.unmodifiableList(this.lootItems);
    }

    private void loadRandomItemsInGame() {
        Random random = new Random();

        lootItems.add(createItem(null, "Apple", random));
        lootItems.add(createItem(null, "Wood", random));
        lootItems.add(createItem(null, "Stone", random));
        lootItems.add(createItem(null, "Iron Ore", random));
        lootItems.add(createItem(null, "Gold Ore", random));
        lootItems.add(createItem(null, "Diamond", random));
        lootItems.add(createItem(null, "Stick", random));
        lootItems.add(createItem(null, "Torch", random));
        lootItems.add(createItem(null, "Bread", random));
        lootItems.add(createItem(null, "Leather", random));
        lootItems.add(createItem(null, "Coal", random));
        lootItems.add(createItem(null, "Sword", random));
        lootItems.add(createItem(null, "Pickaxe", random));
        lootItems.add(createItem(null, "Bow", random));
        lootItems.add(createItem(null, "Arrow", random));
        lootItems.add(new Item(null, "Magic brush", new Coordinates(null, 0, 0)));
    }

    private Item createItem(Long itemId, String name, Random random) {
        int x = random.nextInt(WORLD_WIDTH);
        int y = random.nextInt(WORLD_HEIGHT);

        Coordinates coordinates = new Coordinates(itemId, x, y);
        return new Item(itemId, name, coordinates);
    }

}
