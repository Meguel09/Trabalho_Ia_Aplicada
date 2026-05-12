package com.example.barcrud.controller;

// ESTES SÃO OS IMPORTS QUE ESTÃO FALTANDO:
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}