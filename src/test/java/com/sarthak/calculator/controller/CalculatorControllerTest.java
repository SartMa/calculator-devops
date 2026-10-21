package com.sarthak.calculator.controller;

import com.sarthak.calculator.services.CalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculatorController.class)
@Import(CalculatorService.class)

class CalculatorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void addReturnsJsonResult() throws Exception {
        mockMvc.perform(get("/api/v1/add").param("a", "2").param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("add"))
                .andExpect(jsonPath("$.result").value(5.0));
    }

    @Test
    void multiplyReturnsJsonResult() throws Exception {
        mockMvc.perform(get("/api/v1/multiply").param("a", "2").param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("multiply"))
                .andExpect(jsonPath("$.result").value(6.0));
    }

    @Test
    void subtractReturnsJsonResult() throws Exception {
        mockMvc.perform(get("/api/v1/subtract").param("a", "2").param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("subtract"))
                .andExpect(jsonPath("$.result").value(-1.0));
    }

    @Test
    void divideReturnsJsonResult() throws Exception {
        mockMvc.perform(get("/api/v1/divide").param("a", "2").param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("divide"))
                .andExpect(jsonPath("$.result").value(2/3));
    }

    @Test
    void divideByZeroTest() throws Exception {
        mockMvc.perform(get("/api/v1/divide").param("a", "2").param("b", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Division by Zero"));
    }

    @Test
    void missingParameterTest() throws Exception {
        mockMvc.perform(get("/api/v1/subtract").param("a", "2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Required Parameter b missing."));
    }

    @Test
    void invalidParameterTest() throws Exception {
        mockMvc.perform(get("/api/v1/divide").param("a", "hello").param("b", "2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Parameter a must be a number, but was hello"));
    }
}