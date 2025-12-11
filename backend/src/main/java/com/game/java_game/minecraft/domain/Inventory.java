package com.game.java_game.minecraft.domain;

import java.util.Collections;
import java.util.List;

import com.game.java_game.minecraft.domain.exception.InvalidGameOperationException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Inventory {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(targetEntity= Item.class, cascade= CascadeType.ALL, orphanRemoval=true)
    private List<Item> inventoryItems;

    public Inventory() {
    }

    public Inventory(Long id, List<Item> inventoryItems) {
        this.id = id;
        this.inventoryItems = inventoryItems;
    }

    public List<Item> getInventoryItems() {
        return Collections.unmodifiableList(this.inventoryItems);
    }

    public void addItem(Item item) {
        this.inventoryItems.add(item);
        item.isInInventory();
    }

    public void removeItem(Item itemToRemove, Coordinates coordinates) {
        if(this.inventoryItems.contains(itemToRemove) == false){
            throw new InvalidGameOperationException("You cannot drop an item that you do not possess");
        }
        this.inventoryItems.removeIf((item -> item.getId().equals(itemToRemove.getId())));
        itemToRemove.isRemovedFromInventory(coordinates.getX(), coordinates.getY());
    }
}
