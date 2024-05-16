package com.github.julioevencio.apifiles.domain.exceptions;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ApiFilesMessageError implements Serializable {

    private final String message;
    private final List<String> errors;
    private final LocalDateTime timestamp;

    public ApiFilesMessageError(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
