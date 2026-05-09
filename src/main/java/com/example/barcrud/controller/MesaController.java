package com.example.barcrud.controller;

import com.example.barcrud.dto.MesaRequest;
import com.example.barcrud.model.Mesa;
import com.example.barcrud.model.StatusMesa;
import com.example.barcrud.service.MesaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/mesas")
public class MesaController {
    private final MesaService service;

    public MesaController(MesaService service) { this.service = service; }

    @PostMapping
    public Mesa criar(@RequestBody @Valid MesaRequest request) { return service.criar(request); }

    @GetMapping
    public List<Mesa> listar(@RequestParam(required=false) StatusMesa status) {
        return status == null ? service.listar() : service.listarPorStatus(status);
    }

    @GetMapping("/{id}")
    public Mesa buscar(@PathVariable Long id) { return service.buscar(id); }

    @PutMapping("/{id}")
    public Mesa atualizar(@PathVariable Long id, @RequestBody @Valid MesaRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) { service.excluir(id); }
}