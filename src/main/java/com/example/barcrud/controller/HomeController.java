package com.example.barcrud.controller;

// ESTES SÃO OS IMPORTS QUE ESTÃO FALTANDO:
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Alterado para /home, pois o LoginController já usará o /login
    @GetMapping("/home")
    public String home() {
        return "home"; // Crie um template home.html para essa página no futuro
    }
}