package com.game.java_game.minecraft.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Item {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne(targetEntity= Coordinates.class, cascade = CascadeType.ALL)
    private Coordinates coordinates;

    public Item() {
    }

    public Item(Long id, String name, Coordinates coordinates) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
 
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getY() {
        return this.coordinates.getY();
    }

    public int getX() {
        return this.coordinates.getX();
    }

    private Coordinates getCoordinates() {
        return this.coordinates;
    }

    public void isInInventory() {
        this.coordinates.remove();
    }

    public void isRemovedFromInventory(int x, int y) {
        this.coordinates.add(x, y);
    }

    @Override
    public String toString() {
        return String.format("""
                {
                  "id": %d,
                  "name": "%s",
                  "coordinates": %s
                }
                """, this.getId(), this.getName(), this.getCoordinates().toString());
    }

}
