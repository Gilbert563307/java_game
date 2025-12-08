package com.game.java_game.minecraft.domain;

import com.game.java_game.minecraft.domain.enums.Direction;

public class Player extends Character {
    private Long id;
    private String username;

    public Player(Long id, String username, int health, int experience, Inventory inventory, Coordinates coordinates) {
        super(health, experience, inventory, coordinates);
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return this.username;
    }

    public void pickUpItem(Item item) {
        int playerY = this.coordinates.getY();
        int playerX = this.coordinates.getX();
        int itemY = item.getY();
        int itemX = item.getX();

        if (playerY == itemY && playerX == itemX) {
            this.inventory.addItem(item);
        }
    }

    public void dropItem(Item item) {
        this.inventory.removeItem(item, this.coordinates);
    }

    protected  void move(Direction direction) {
        this.coordinates.update(direction);
    }

    @Override
    public String toString() {
        return String.format("""
                {
                  "id": %d,
                  "username": "%s",
                  "health": %d,
                  "experience": %d,
                  "coordinates": %s
                }
                """, this.getId(), this.getUsername(), this.getHealth(), this.getExperience(),
                this.getCoordinates().toString());
    }

}
