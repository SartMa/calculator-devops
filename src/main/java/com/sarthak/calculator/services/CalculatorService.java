package com.sarthak.calculator.services;

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

    public double divide(double divisor, double dividend) throws Exception{
        if(dividend == 0)throw new Exception("Zero divide");
        return divisor/dividend;
    }
}
