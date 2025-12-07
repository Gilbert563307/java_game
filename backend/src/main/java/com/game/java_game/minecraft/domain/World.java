package com.game.java_game.minecraft.domain;


public class World {
    private Long id;
    private String name;

    public World(){

    }

    public World(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
