package com.comp202.ums.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(value = {AuthorizationException.class})
    public ResponseEntity<?> handleAuthorizationException(AuthorizationException e, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(new Date(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<?> handleForbiddenException(ForbiddenException e, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(new Date(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(NotFoundException e, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(new Date(), e.getMessage(), e.getDetails());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException e, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(new Date(), e.getMessage());
        Throwable cause = e.getCause();
        if (cause instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) cause;
            String columnName = constraintViolationException.getSQLException().getMessage();
            errorResponse.setDetails(columnName);
        }

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleExceptions(Exception e, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
