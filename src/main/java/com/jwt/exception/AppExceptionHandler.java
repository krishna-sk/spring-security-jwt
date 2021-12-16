package com.jwt.exception;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ErrorMessage errorMessage;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        errorMessage.setTimestamp(new Date());
        errorMessage.setStatus(status.value());
        errorMessage.setMessage(errors);

        return new ResponseEntity<>(errorMessage, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        errorMessage.setTimestamp(new Date());
        errorMessage.setStatus(status.value());
        errorMessage.setMessage(Arrays.asList(exception.getMostSpecificCause().toString()));

        return new ResponseEntity<>(errorMessage, status);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsExceptionHandler(BadCredentialsException exception) {

        errorMessage.setTimestamp(new Date());
        errorMessage.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorMessage.setMessage(Arrays.asList(exception.getMessage()));

        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleExceptionHandler(RuntimeException exception) {

        errorMessage.setTimestamp(new Date());
        errorMessage.setStatus(HttpStatus.BAD_REQUEST.value());
        errorMessage.setMessage(Arrays.asList(exception.getMessage()));

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);

    }

}
