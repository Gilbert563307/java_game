package com.game.java_game.minecraft.domain;

public abstract class Character {
    protected int health;
    protected int experience;
    protected Inventory inventory;
    protected Coordinates coordinates;

    protected Character(int health, int experience, Inventory inventory, Coordinates coordinates) {
        this.health = health;
        this.experience = experience;
        this.inventory = inventory;
        this.coordinates = coordinates;
    }

    public int getHealth() { return this.health; }
    public int getExperience() { return this.experience; }

    public Inventory getInventory() {
        return this.inventory;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }
}
