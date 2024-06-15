package br.com.nevesHoteis.infra.exeption;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<?> notFoundException (Exception e){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> methordArgument(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest().body( new erroFormation(e.getFieldError().getField(), e.getFieldError().getDefaultMessage()));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<?> methordArgument(HttpMessageNotReadableException e){
        return ResponseEntity.badRequest().body( new erroFormation("Error HTTP", e.getMessage() ));
    }
    @ExceptionHandler(SQLException.class)
    ResponseEntity<?> sqlException(SQLException e){
        return ResponseEntity.badRequest().body( new erroFormation("Error SQL", e.getMessage() ));
    }
    private record erroFormation(
            String field,
            String message
    ){

    }

}
