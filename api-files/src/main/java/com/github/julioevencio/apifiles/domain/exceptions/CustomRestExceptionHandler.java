package com.github.julioevencio.apifiles.domain.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomRestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiFilesMessageError> handlerException(Exception e) {
        ApiFilesMessageError error = new ApiFilesMessageError("Bad Request", List.of("Bad request..."));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiFilesMessageError> handlerRuntimeException(RuntimeException e) {
        ApiFilesMessageError error = new ApiFilesMessageError("Bad Request", List.of("Bad request..."));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiFilesMessageError> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

        ApiFilesMessageError error = new ApiFilesMessageError("Invalid data", errors);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(ApiFilesSQLException.class)
    public ResponseEntity<ApiFilesMessageError> handlerApiFilesSQLException(ApiFilesSQLException e) {
        ApiFilesMessageError error = new ApiFilesMessageError("Invalid data", List.of(e.getMessage()));

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

}