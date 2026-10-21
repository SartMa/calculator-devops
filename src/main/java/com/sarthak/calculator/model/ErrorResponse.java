package com.sarthak.calculator.model;

public record ErrorResponse(int status, String error, String message) {

}