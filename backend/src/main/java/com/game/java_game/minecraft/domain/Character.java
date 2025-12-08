package com.game.java_game.minecraft.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;

//map this class or jpa will not see it
@MappedSuperclass
public abstract class Character {
    protected int health;
    protected int experience;
    @OneToOne(cascade= CascadeType.ALL)
    protected Inventory inventory;

    @OneToOne(cascade = CascadeType.ALL)
    protected Coordinates coordinates;

    protected Character(){

    }

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
