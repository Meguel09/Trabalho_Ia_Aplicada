package com.example.barcrud.controller;

import com.example.barcrud.model.Usuario;
import com.example.barcrud.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Retorna a página visual do seu HTML
    @GetMapping("/login")
    public String exibirTelaLogin() {
        return "login";
    }

    // Recebe os dados do formulário quando o botão "ENTRAR" é clicado
    @PostMapping("/login")
    public String processarLogin(@RequestParam("email") String email, 
                                 @RequestParam("password") String password, 
                                 Model model, 
                                 HttpSession session) {
        
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario != null && usuario.getSenha().equals(password)) {
            session.setAttribute("usuarioLogado", usuario);
            return "redirect:/home"; 
        } else {
            model.addAttribute("erro", "E-mail ou senha inválidos. Tente novamente.");
            return "login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}