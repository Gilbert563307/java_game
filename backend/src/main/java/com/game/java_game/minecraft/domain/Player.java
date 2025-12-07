package com.game.java_game.minecraft.domain;

import com.game.java_game.minecraft.domain.enums.Direction;

public class Player {
    private Long id;
    private String username;
    private Number health;
    private Number experience;
    private Cordinations cordinations;

    public Player(){

    }

    public Player(Long id, String username, Number health, Number experience, Cordinations cordinations) {
        this.id = id;
        this.username = username;
        this.health = health;
        this.experience = experience;
        this.cordinations = cordinations;
    }


    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Number getHealth() {
        return health;
    }

    public Number getExperience() {
        return experience;
    }

    public void update(Number health, Number experience) {
        this.health = health;
        this.experience = experience;
    }

    public void updateUserName(String username){
        this.username = username;
    }


    public void updateLocation(Direction direction) {
        switch ()
    }
}
