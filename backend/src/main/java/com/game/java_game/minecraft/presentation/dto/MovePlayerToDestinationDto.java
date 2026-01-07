package com.game.java_game.minecraft.presentation.dto;

public class MovePlayerToDestinationDto {
    private int x;
    private int y;

    public MovePlayerToDestinationDto(){

    }

    public MovePlayerToDestinationDto(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
