package com.jonatask.kyc.infrastructure.config;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException ex) {
        if (ex.getTargetType().isEnum()) {
            String allowedValues = Arrays.stream(ex.getTargetType().getEnumConstants())
                .map(Object::toString)
                .collect(Collectors.joining(", "));

            String message = String.format(
                "Invalid document type '%s'. Allowed values: %s",
                ex.getValue(),
                allowedValues
            );

            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(message));
        }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse("Invalid request format"));
    }
}

