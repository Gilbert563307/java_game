package com.game.java_game.minecraft.domain;

public class Cordinations {
    private Long id;
    private Number x;
    private Number y;
    private Number z;

    public Cordinations() {
    }

    public Cordinations(Long id, Number x, Number y, Number z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void update( Number x, Number y, Number z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Long getId() {
        return id;
    }

    public Number getX() {
        return x;
    }

    public Number getY() {
        return y;
    }

    public Number getZ() {
        return z;
    }
}
