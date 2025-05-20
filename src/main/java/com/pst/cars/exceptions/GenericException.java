package com.pst.cars.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class GenericException extends RuntimeException {

    public final static String GENERIC_MESSAGE = "Please contact administrator";

    private final HttpStatus status;
    private final String message;
    private final String error;

    public GenericException (
            HttpStatusCode status,
            String message,
            String error
    ) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, message, error);
    }

    public GenericException (
            HttpStatus status,
            String message,
            String error
    ) {
        this.status = status;
        this.message = message;
        this.error = error;
    }

    public static GenericException internalServerError() {
        return new GenericException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                GENERIC_MESSAGE,
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        );
    }

}
