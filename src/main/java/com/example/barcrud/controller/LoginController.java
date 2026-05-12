package com.example.barcrud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String exibirTelaLogin(Model model) {
        // Se o usuário já está autenticado, redireciona para home
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && 
            !authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/home";
        }
        return "login";
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}