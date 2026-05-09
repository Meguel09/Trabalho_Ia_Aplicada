package com.example.barcrud.controller;

import com.example.barcrud.dto.ProdutoRequest;
import com.example.barcrud.model.Produto;
import com.example.barcrud.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService service;

    public ProdutoController(ProdutoService service) { this.service = service; }

    @PostMapping
    public Produto criar(@RequestBody @Valid ProdutoRequest request) { return service.criar(request); }

    @GetMapping
    public List<Produto> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public Produto buscar(@PathVariable Long id) { return service.buscar(id); }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @RequestBody @Valid ProdutoRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) { service.excluir(id); }
}