package com.sarthak.calculator.controller;

import com.sarthak.calculator.services.CalculatorService;
import com.sarthak.calculator.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CalculatorController {
    private final CalculatorService service;
    double res;
    CalculatorController(CalculatorService service){
        this.service = service;
    }
    @GetMapping("add")
    public Result add(@RequestParam() double a, @RequestParam() double b) {
        res = service.add(a, b);
        return new Result("add", a, b, res);
    }

    @GetMapping("multiply")
    public Result multiply(@RequestParam() double a, @RequestParam double b){
        res = service.multiply(a, b);
        return new Result("multiply", a, b, res);
    }
}