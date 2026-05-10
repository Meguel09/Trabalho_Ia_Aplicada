package com.example.barcrud.service;

import com.example.barcrud.dto.ItemPedidoRequest;
import com.example.barcrud.dto.PedidoRequest;
import com.example.barcrud.exception.BusinessException;
import com.example.barcrud.exception.NotFoundException;
import com.example.barcrud.model.*;
import com.example.barcrud.repository.ContaRepository;
import com.example.barcrud.repository.ItemPedidoRepository;
import com.example.barcrud.repository.PedidoRepository;
import com.example.barcrud.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ContaRepository contaRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ContaRepository contaRepository,
                         ProdutoRepository produtoRepository, ItemPedidoRepository itemPedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.contaRepository = contaRepository;
        this.produtoRepository = produtoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public Pedido criar(PedidoRequest request) {
        Conta conta = contaRepository.findById(request.contaId())
                .orElseThrow(() -> new NotFoundException("Conta não encontrada."));
        if (conta.getStatus() == StatusConta.FECHADA) {
            throw new BusinessException("Não é permitido criar pedido em conta fechada.");
        }
        return pedidoRepository.save(new Pedido(conta));
    }

    public List<Pedido> listar() { return pedidoRepository.findAll(); }

    public Pedido buscar(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new NotFoundException("Pedido não encontrado."));
    }

    public Pedido atualizar(Long id, PedidoRequest request) {
        Pedido pedido = buscar(id);
        if (pedido.getConta().getStatus() == StatusConta.FECHADA) {
            throw new BusinessException("Não é possível editar pedido de conta fechada.");
        }

        Conta conta = contaRepository.findById(request.contaId())
                .orElseThrow(() -> new NotFoundException("Conta não encontrada."));
        if (conta.getStatus() == StatusConta.FECHADA) {
            throw new BusinessException("Não é permitido mover pedido para conta fechada.");
        }
        pedido.setConta(conta);
        return pedidoRepository.save(pedido);
    }

    public void excluir(Long id) {
        Pedido pedido = buscar(id);
        if (pedido.getConta().getStatus() == StatusConta.FECHADA) {
            throw new BusinessException("Não é possível excluir pedido de conta fechada.");
        }
        pedidoRepository.delete(pedido);
    }

    public ItemPedido adicionarItem(Long pedidoId, ItemPedidoRequest request) {
        Pedido pedido = buscar(pedidoId);
        if (pedido.getConta().getStatus() == StatusConta.FECHADA) {
            throw new BusinessException("Não é permitido adicionar item em conta fechada.");
        }
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));
        if (!produto.getAtivo()) {
            throw new BusinessException("Produto inativo não pode ser vendido.");
        }
        return itemPedidoRepository.save(new ItemPedido(pedido, produto, request.quantidade()));
    }

    public void removerItem(Long pedidoId, Long itemId) {
        Pedido pedido = buscar(pedidoId);;

        ItemPedido item = itemPedidoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item do pedido não encontrado."));

        if (!item.getPedido().getId().equals(pedido.getId())) {
            throw new RuntimeException("Este item não pertence ao pedido informado.");
        }

        if (pedido.getConta().getStatus() == StatusConta.FECHADA) {
            throw new RuntimeException("Não é possível remover item de pedido de uma conta fechada.");
        }

        itemPedidoRepository.delete(item);
    }
}
