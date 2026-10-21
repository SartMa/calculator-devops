package com.sarthak.calculator.services;

import com.sarthak.calculator.exception.ZeroDivideException;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    public double add(double num1, double num2){
        return num1 + num2;
    }

    public double multiply(double num1, double num2){
        return num1 * num2;
    }

    public double subtract(double num1, double num2){
        return num1 - num2;
    }

    public double divide(double divisor, double dividend) throws ZeroDivideException {
        if(dividend == 0)throw new ZeroDivideException("Division by Zero");
        return divisor/dividend;
    }
}
