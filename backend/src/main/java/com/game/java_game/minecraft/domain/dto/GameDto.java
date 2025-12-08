package com.game.java_game.minecraft.domain.dto;

public class GameDto {
    private Long id;
    private String worldName;
    private WorldMapDto worldMapDto;
    
    public GameDto() {
    }
    
    public GameDto(Long id, String worldName, WorldMapDto worldMapDto) {
        this.id = id;
        this.worldName = worldName;
        this.worldMapDto = worldMapDto;
    }

    public Long getId() {
        return id;
    }
    public String getWorldName() {
        return worldName;
    }

    public WorldMapDto getWorld() {
        return worldMapDto;
    }
}
