package com.game.java_game.minecraft.presentation.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.game.java_game.minecraft.domain.exception.InvalidGameOperationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidGameOperationException.class)
    public ResponseEntity<Map<String, String>> handleInvalidGameOperation(InvalidGameOperationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
    }

}
