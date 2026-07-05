package com.todo.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.net.URI;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ProblemDetail handleTaskNotFound(
        TaskNotFoundException ex,
        WebRequest request
    ) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND,
            ex.getMessage()
        );
        pd.setTitle("Not found");
        pd.setType(URI.create("about:blank"));
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty(
            "path",
            request.getDescription(false).replace("uri=", "")
        );
        return pd;
    }

    @ExceptionHandler(InvalidStatusException.class)
    public ProblemDetail handleInvalidStatus(
        InvalidStatusException ex,
        WebRequest request
    ) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            ex.getMessage()
        );
        pd.setTitle("Bad Request");
        pd.setType(URI.create("about:blank"));
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty(
            "path",
            request.getDescription(false).replace("uri=", "")
        );
        return pd;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex,
        WebRequest request
    ) {
        String message = "Malformed request body";

        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException ife) {
            if (ife.getTargetType() != null && ife.getTargetType().isEnum()) {
                String validValues = Arrays.toString(
                    ife.getTargetType().getEnumConstants()
                );
                message =
                    "Invalid value '" +
                    ife.getValue() +
                    "' for field '" +
                    ife.getPath().stream()
                        .map(JsonMappingException.Reference::getFieldName)
                        .collect(Collectors.joining(".")) +
                    "'. Allowed: " +
                    validValues;
            }
        }

        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            message
        );
        pd.setTitle("Bad Request");
        pd.setType(URI.create("about:blank"));
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty(
            "path",
            request.getDescription(false).replace("uri=", "")
        );
        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(
        MethodArgumentNotValidException ex,
        WebRequest request
    ) {
        List<String> errors = ex
            .getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
            .collect(Collectors.toList());

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Validation Failed");
        pd.setDetail("Request validation failed");
        pd.setType(URI.create("about:blank"));
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty(
            "path",
            request.getDescription(false).replace("uri=", "")
        );
        pd.setProperty("errors", errors);
        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneral(Exception ex, WebRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "An unexpected error occurred"
        );
        pd.setTitle("Internal Server Error");
        pd.setType(URI.create("about:blank"));
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty(
            "path",
            request.getDescription(false).replace("uri=", "")
        );
        return pd;
    }
}
