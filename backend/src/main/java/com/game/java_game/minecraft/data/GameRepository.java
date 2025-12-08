package com.game.java_game.minecraft.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.java_game.minecraft.domain.Game;
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

}
