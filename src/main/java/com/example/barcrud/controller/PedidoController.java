package com.example.barcrud.controller;

import com.example.barcrud.dto.ItemPedidoRequest;
import com.example.barcrud.dto.PedidoRequest;
import com.example.barcrud.model.ItemPedido;
import com.example.barcrud.model.Pedido;
import com.example.barcrud.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService service;

    public PedidoController(PedidoService service) { this.service = service; }

    @PostMapping
    public Pedido criar(@RequestBody @Valid PedidoRequest request) { return service.criar(request); }

    @GetMapping
    public List<Pedido> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public Pedido buscar(@PathVariable Long id) { return service.buscar(id); }

    @PutMapping("/{id}")
    public Pedido atualizar(@PathVariable Long id, @RequestBody @Valid PedidoRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) { service.excluir(id); }

    @PostMapping("/{id}/itens")
    public ItemPedido adicionarItem(@PathVariable Long id, @RequestBody @Valid ItemPedidoRequest request) {
        return service.adicionarItem(id, request);
    }

    @DeleteMapping("/itens/{itemId}")
    public void removerItem(@PathVariable Long itemId) { service.removerItem(itemId); }
}