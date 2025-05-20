package com.pst.cars.controllers;

import com.pst.cars.exceptions.GenericException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException (Exception e) {

        e.printStackTrace();
        log.error("System encountered an exception {}", ExceptionUtils.getMessage(e));

        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var error = ApiError.of(
                String.valueOf(status.value()),
                GenericException.GENERIC_MESSAGE,
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        );
        return ResponseEntity
                .status(500)
                .body(error);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ApiError> handleGenericException (GenericException e) {

        e.printStackTrace();
        log.error("System encountered a generic exception {}", ExceptionUtils.getMessage(e));

        var code = e.getStatus().value();
        var error = ApiError.of(String.valueOf(code), e.getMessage(), e.getError());

        return ResponseEntity
                .status(code)
                .body(error);
    }

    public record ApiError (String code, String message, String error) {
        static ApiError of(String code, String message, String error) {
            return new ApiError(code, message, error);
        }
    }

}
