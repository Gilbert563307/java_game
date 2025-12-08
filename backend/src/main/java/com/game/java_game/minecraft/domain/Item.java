package com.game.java_game.minecraft.domain;

public class Item {
    private Long id;
    private String name;
    private Coordinates coordinates;
    // private boolean isInInventory;

    public Item() {
    }

    public Item(Long id, String name, Coordinates coordinates) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        // this.isInInventory = false;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getY(){
        return this.coordinates.getY();
    }

    public int getX(){
        return this.coordinates.getX();
    }

    private  Coordinates getCoordinates() {
        return this.coordinates;
    }

    public void isInInventory(){
        this.coordinates.remove();
    }

    public void isRemovedFromInventory(int x, int y){
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
