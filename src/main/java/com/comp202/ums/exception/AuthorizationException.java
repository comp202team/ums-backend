package com.comp202.ums.exception;

public class AuthorizationException extends RuntimeException{

    @Override
    public String getMessage() {
        return "Unauthorized";
    }
}