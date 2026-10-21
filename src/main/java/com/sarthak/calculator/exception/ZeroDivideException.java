package com.sarthak.calculator.exception;

public class ZeroDivideException extends RuntimeException{
    public ZeroDivideException(String message){
        super(message);
    }
}
