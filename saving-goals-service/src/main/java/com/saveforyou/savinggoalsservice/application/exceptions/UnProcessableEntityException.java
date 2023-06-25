package com.saveforyou.savinggoalsservice.application.exceptions;

public class UnProcessableEntityException extends RuntimeException {

    public UnProcessableEntityException(String message){
        super(message);
    }
}
