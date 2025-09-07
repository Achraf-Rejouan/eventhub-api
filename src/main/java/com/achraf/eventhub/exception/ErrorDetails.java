package com.achraf.eventhub.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorDetails(
        LocalDateTime timestamp,
        String message,
        String details,
        String path,
        int status,
        List<String> errors
) {
    public ErrorDetails(String message, String details, String path, int status) {
        this(LocalDateTime.now(), message, details, path, status, null);
    }

    public ErrorDetails(String message, String details, String path, int status, List<String> errors) {
        this(LocalDateTime.now(), message, details, path, status, errors);
    }
}
