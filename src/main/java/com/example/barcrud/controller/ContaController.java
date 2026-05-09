package com.example.barcrud.controller;

import com.example.barcrud.dto.AbrirContaRequest;
import com.example.barcrud.dto.PagamentoRequest;
import com.example.barcrud.model.Conta;
import com.example.barcrud.model.Pagamento;
import com.example.barcrud.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {
    private final ContaService service;

    public ContaController(ContaService service) { this.service = service; }

    @PostMapping("/abrir")
    public Conta abrir(@RequestBody @Valid AbrirContaRequest request) { return service.abrir(request); }

    @GetMapping
    public List<Conta> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public Conta buscar(@PathVariable Long id) { return service.buscar(id); }

    @PutMapping("/{id}")
    public Conta atualizar(@PathVariable Long id, @RequestBody @Valid AbrirContaRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) { service.excluir(id); }

    @PostMapping("/{id}/fechar")
    public Conta fechar(@PathVariable Long id) { return service.fechar(id); }

    @PostMapping("/{id}/pagamentos")
    public Pagamento pagar(@PathVariable Long id, @RequestBody @Valid PagamentoRequest request) {
        return service.pagar(id, request);
    }

    @GetMapping("/{id}/imprimir")
    public String imprimir(@PathVariable Long id) { return service.imprimir(id); }
}