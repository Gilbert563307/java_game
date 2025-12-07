package com.game.java_game.minecraft.domain;


import com.game.java_game.minecraft.domain.enums.Direction;

import java.util.List;

public class Game {
    private Long id;
    private Session session;
    private World world;
    private boolean gameStarted;


    public Game(){

    }

    public Game(Long id) {
        this.id = id;
        this.session = null;
        this.world = null;
        this.gameStarted = false;
    }

    public void startGame(World world){
        if(this.gameStarted){
            throw  new RuntimeException("Game has already started");
        }

        if(world == null){
            this.world = new World(0L, "Your world");
        }else{
            this.world = world;
        }
        this.createSession();
        this.gameStarted = true;
    }

    private void createSession(){
        this.session = new Session(0L);
    }

    private Session getSession(){
        if(this.session == null){
            throw  new RuntimeException("There is no session");
        }
        return this.session;
    }

    public void joinGame(Player player){
        if(!this.gameStarted){
            throw  new RuntimeException("You cannot join the game while its not started");
        };
        Session currSession = this.getSession();
        currSession.join(player);
    }

    public void leaveGame(Player player){
        if(!this.gameStarted){
            throw  new RuntimeException("You cannot leave the game while its not started");
        };
        Session currSession = this.getSession();
        currSession.leave(player);
    }

    public void quitGme(){
        this.gameStarted = false;
        this.world = null;
        this.session = null;
    }

    public void movePlayer(Player player, Direction direction){
        Session currSession = this.getSession();
        boolean isPlayerInCurrentSession = currSession.isPlayerInCurrentSession(player);
        if(!isPlayerInCurrentSession){
            throw new RuntimeException("You cannot move a player who is not in the game");
        }
        currSession.movePlayer(player, direction);
    }



    public List<String> getGameSession(){
        return this.getSession().getPlayers();
    }
}
