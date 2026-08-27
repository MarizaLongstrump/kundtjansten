package com.mariza.customer.exceptions;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@ResponseStatus // Viktig vid API Controle
//Spring ska använda denna klass för att fånga fel från
// alla controllers som returnerar JSON
@RestControllerAdvice
public class GlobalExceptionHandler {


        // det hanterar validering Erros från request
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();

            ex.getBindingResult().getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );

            return errors;
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public Map<String, String> handleNotFound(ResourceNotFoundException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getMessage());
            return error;
        }
    }



