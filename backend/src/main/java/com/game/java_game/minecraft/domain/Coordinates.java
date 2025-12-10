package com.game.java_game.minecraft.domain;

import com.game.java_game.minecraft.domain.enums.Direction;
import com.game.java_game.minecraft.presentation.exception.BadRequestException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Coordinates {
    @Id
    @GeneratedValue
    private Long id;
    private int y;
    private int x;

    public Coordinates() {
    }

    public Coordinates(Long id, int x, int y) {
        this.id = id;
        this.y = y;
        this.x = x;
    }

    private void updateCoordinates(int y, int x) {
        if (y >= 0 && y < World.WORLD_HEIGHT) {
            this.y = y;
        }

        if (x >= 0 && x < World.WORLD_WIDTH) {
            this.x = x;
        }
    }

    public Long getId() {
        return this.id;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void remove() {
        this.x = 0;
        this.y = 0;
    }

    public void add(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(Direction direction) {
        switch (direction) {
        case UP:
            this.updateCoordinates(this.getY() + 1, this.getX());
            break;
        case DOWN:
            this.updateCoordinates(this.getY() - 1, this.getX());
            break;
        case RIGHT:
            this.updateCoordinates(this.getY(), this.getX() + 1);
            break;
        case LEFT:
            this.updateCoordinates(this.getY(), this.getX() - 1);
            break;
        default:
            throw new BadRequestException("No direction found to update coordinates");
        }
    }

    @Override
    public String toString() {
        return String.format("""
                {
                    "y": %d,
                    "x": %d
                }
                """, this.getX(), this.getY());
    }

}
