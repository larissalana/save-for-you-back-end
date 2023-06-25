package com.saveforyou.savinggoalsservice.application.exceptions;

public class NoSuchElementFoundException extends RuntimeException {

    public NoSuchElementFoundException(String message){
        super(message);
    }
}
