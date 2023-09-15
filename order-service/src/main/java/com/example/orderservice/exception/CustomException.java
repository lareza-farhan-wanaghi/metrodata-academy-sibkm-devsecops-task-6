package com.example.orderservice.exception;

import lombok.Data;

@Data
public class CustomException extends RuntimeException{

    private String error; // ORDER_NOT_FOUND
    private int status; // 404/500/409

    public CustomException(String message, String error, int status) {
        super(message);
        this.error = error;
        this.status = status;
    }
}
