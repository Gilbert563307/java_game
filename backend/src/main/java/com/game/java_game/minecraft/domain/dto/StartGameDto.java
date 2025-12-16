package com.game.java_game.minecraft.domain.dto;

public class StartGameDto {
    private Long id;
    private String worldName;
    private WorldMapDto worldMapDto;
    private Long playerId;

    public StartGameDto() {
    }
    

    public StartGameDto(Long id, String worldName, WorldMapDto worldMapDto, Long playerId) {
        this.id = id;
        this.worldName = worldName;
        this.worldMapDto = worldMapDto;
        this.playerId = playerId;
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

    public Long getPlayerId() {
        return playerId;
    }

}
