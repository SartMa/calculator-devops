package com.sarthak.calculator.services;

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
        assertEquals(15.0, service.add(5.0, 3.0), 1e-9);
    }

    @Test
    void TestSubtractReturnSubtraction(){
        assertEquals(2.0, service.add(5.0, 3.0), 1e-9);
    }
}