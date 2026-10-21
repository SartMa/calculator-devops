package com.sarthak.calculator.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {
    private final CalculatorService service = new CalculatorService();

    @Test
    void TestAddReturnsSum(){
        assertEquals(5.0, service.add(2.0, 3.0), 1e-9);
    }
}