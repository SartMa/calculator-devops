package com.sarthak.calculator.services;

import com.sarthak.calculator.exception.ZeroDivideException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {
    private final CalculatorService service = new CalculatorService();

    @Test
    void TestAddReturnsSum(){
        assertEquals(5.0, service.add(2.0, 3.0), 1e-9);
    }

    @Test
    void TestMultiplyReturnsMultiplication(){
        assertEquals(15.0, service.multiply(5.0, 3.0), 1e-9);
    }

    @Test
    void TestSubtractReturnSubtraction(){
        assertEquals(2.0, service.subtract(5.0, 3.0), 1e-9);
    }

    @Test
    void TestDivideReturnDivision(){
        assertEquals(2.5, service.divide(5.0, 2.0), 1e-9);
    }

    @Test
    void TestDivideByZero(){
        assertThrows(ZeroDivideException.class, () ->  service.divide(5, 0));
    }
}