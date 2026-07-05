package com.todo.exception;

public class InvalidStatusException extends RuntimeException {

    private final String invalidValue;

    public InvalidStatusException(String invalidValue) {
        super(
            "Invalid status value: '" +
                invalidValue +
                "'. Allowed: PENDING, COMPLETED"
        );
        this.invalidValue = invalidValue;
    }

    public String getInvalidValue() {
        return invalidValue;
    }
}
