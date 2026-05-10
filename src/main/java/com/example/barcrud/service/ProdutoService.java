package com.example.barcrud.service;

import com.example.barcrud.dto.ProdutoRequest;
import com.example.barcrud.exception.BusinessException;
import com.example.barcrud.exception.NotFoundException;
import com.example.barcrud.model.Produto;
import com.example.barcrud.repository.ItemPedidoRepository;
import com.example.barcrud.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;

    public ProdutoService(ProdutoRepository repository, ItemPedidoRepository itemPedidoRepository) {
        this.repository = repository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public Produto criar(ProdutoRequest request) {
        return repository.save(new Produto(request.nome().trim(), request.categoria().trim(), request.preco()));
    }

    public List<Produto> listar() { return repository.findAll(); }

    public Produto buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Produto não encontrado."));
    }

    public Produto atualizar(Long id, ProdutoRequest request) {
        Produto produto = buscar(id);
        produto.setNome(request.nome().trim());
        produto.setCategoria(request.categoria().trim());
        produto.setPreco(request.preco());
        produto.setAtivo(request.ativo() == null ? produto.getAtivo() : request.ativo());
        return repository.save(produto);
    }

    public void excluir(Long id) {
        Produto produto = buscar(id);
        if (itemPedidoRepository.existsByProdutoId(id)) {
            produto.setAtivo(false);
            repository.save(produto);
            throw new BusinessException("Produto já foi usado em pedido e não pode ser apagado. Ele foi marcado como inativo.");
        }
        repository.delete(produto);
    }
}
