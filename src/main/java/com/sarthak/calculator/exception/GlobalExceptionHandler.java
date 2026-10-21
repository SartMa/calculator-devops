package com.sarthak.calculator.exception;

import com.sarthak.calculator.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ZeroDivideException.class)
    public ResponseEntity<ErrorResponse> handleZero(ZeroDivideException e) {
        ErrorResponse body = new ErrorResponse(400, "Bad Request", e.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParameters(MissingServletRequestParameterException e){
        ErrorResponse body = new ErrorResponse(400, "Bad Request", "Required Parameter " + e.getParameterName() + " missing.");
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodMismatch(MethodArgumentTypeMismatchException e){
        ErrorResponse body = new ErrorResponse(400, "Bad Request", "Parameter " + e.getName() + " must be a number, but was " + e.getValue());
        return ResponseEntity.badRequest().body(body);
    }
}
