package org.hospital.management.patients.controllers;

import com.google.gson.Gson;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.management.patients.dtos.ErrorMessage;
import org.hospital.management.patients.exceptions.EntityNotFoundException;
import org.hospital.management.patients.exceptions.IncorrectNextPageTokenException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private final Gson gson;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(
        BindException exception
    ) {
        var errors = exception.getBindingResult()
            .getAllErrors()
            .stream()
            .collect(Collectors.toMap(
                error -> ((FieldError) error).getField(),
                DefaultMessageSourceResolvable::getDefaultMessage
            ));

        return createResponseEntity(
            gson.toJson(errors).replace("\"", ""),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({
        HttpMessageNotReadableException.class,
        HttpRequestMethodNotSupportedException.class,
        IncorrectNextPageTokenException.class
    })
    public ResponseEntity<ErrorMessage> handleBadRequestException(Exception exception) {
        return createResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(Exception exception) {
        return createResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorMessage> handleException(Exception exception) {
        return createResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorMessage> createResponseEntity(
        String message,
        HttpStatus status
    ) {
        var logId = UUID.randomUUID();
        log.error("[{}] {}", logId, message);

        return new ResponseEntity<>(new ErrorMessage(logId, message), status);
    }
}
