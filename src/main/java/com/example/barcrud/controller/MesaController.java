package com.example.barcrud.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.barcrud.dto.MesaRequest;
import com.example.barcrud.exception.BusinessException;
import com.example.barcrud.model.Mesa;
import com.example.barcrud.model.StatusMesa;
import com.example.barcrud.service.MesaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/mesas")
public class MesaController {
    private final MesaService service;

    public MesaController(MesaService service) { this.service = service; }

    @GetMapping
    public String listar(@RequestParam(required=false) StatusMesa status, Model model) {
        List<Mesa> mesas = status == null ? service.listar() : service.listarPorStatus(status);
        model.addAttribute("mesas", mesas);
        return "mesas";
    }

    @PostMapping
    public String criar(@ModelAttribute @Valid MesaRequest request, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro: Preencha os campos da mesa corretamente.");
            return "redirect:/mesas";
        }
        try {
            service.criar(request);
            redirectAttributes.addFlashAttribute("successMessage", "Mesa cadastrada com sucesso!");
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/mesas";
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute @Valid MesaRequest request, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro: Preencha os campos da mesa corretamente.");
            return "redirect:/mesas";
        }
        try {
            service.atualizar(id, request);
            redirectAttributes.addFlashAttribute("successMessage", "Mesa atualizada com sucesso!");
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/mesas";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.excluir(id);
            redirectAttributes.addFlashAttribute("successMessage", "Mesa excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/mesas";
    }
}