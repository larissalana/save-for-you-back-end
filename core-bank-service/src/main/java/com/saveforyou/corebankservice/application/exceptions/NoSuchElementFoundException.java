package com.saveforyou.corebankservice.application.exceptions;

public class NoSuchElementFoundException extends RuntimeException {

    public NoSuchElementFoundException(String message){
        super(message);
    }
}
