package com.comp202.ums.exception;

public class ForbiddenException extends RuntimeException{

    @Override
    public String getMessage() {
        return "Forbidden";
    }
}
