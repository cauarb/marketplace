package com.marketplace.api.exception;

import com.marketplace.api.dto.ErroDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarErrosDeValidacao(
            MethodArgumentNotValidException ex) {

        Map<String, String> erros = new HashMap<>();


        for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
            erros.put(erro.getField(), erro.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroDTO> tratarRuntimeException(RuntimeException ex) {


        if (ex.getMessage() != null && ex.getMessage().contains("não encontrado")) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErroDTO(404, ex.getMessage()));
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErroDTO(400, ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroDTO> tratarIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErroDTO(422, ex.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroDTO> tratarErroGenerico(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErroDTO(500, "Erro interno — tente novamente mais tarde"));
    }
}