package com.example.barcrud.service;

import com.example.barcrud.dto.ProdutoRequest;
import com.example.barcrud.exception.BusinessException;
import com.example.barcrud.exception.NotFoundException;
import com.example.barcrud.model.Categoria;
import com.example.barcrud.model.Produto;
import com.example.barcrud.repository.CategoriaRepository;
import com.example.barcrud.repository.ItemPedidoRepository;
import com.example.barcrud.repository.ProdutoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    private final ProdutoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository repository, ItemPedidoRepository itemPedidoRepository,
                          CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public Produto criar(ProdutoRequest request) {
        registrarCategoriaSeNecessario(request.categoria());
        Produto produto = new Produto(request.nome(), normalizarCategoria(request.categoria()), request.preco());
        produto.setId(proximoIdDisponivel());
        produto.setAtivo(request.ativo() == null || request.ativo());
        return repository.save(produto);
    }

    public List<Produto> listar() { return repository.findAll(Sort.by("id").ascending()); }

    public Produto buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Produto nao encontrado."));
    }

    @Transactional
    public Produto atualizar(Long id, ProdutoRequest request) {
        Produto produto = buscar(id);
        registrarCategoriaSeNecessario(request.categoria());
        produto.setNome(request.nome());
        produto.setCategoria(normalizarCategoria(request.categoria()));
        produto.setPreco(request.preco());
        produto.setAtivo(request.ativo() == null ? produto.getAtivo() : request.ativo());
        return repository.save(produto);
    }

    @Transactional
    public void excluir(Long id) {
        Produto produto = buscar(id);
        if (itemPedidoRepository.existsByProdutoId(id)) {
            produto.setAtivo(false);
            repository.save(produto);
            return;
        }
        repository.delete(produto);
    }

    public List<String> listarCategorias() {
        Set<String> categorias = new HashSet<>();
        categoriaRepository.findAll().forEach(categoria -> categorias.add(categoria.getNome()));
        repository.findAll().forEach(produto -> categorias.add(produto.getCategoria()));

        return categorias.stream()
                .filter(categoria -> categoria != null && !categoria.isBlank())
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();
    }

    public void criarCategoria(String nome) {
        String categoria = normalizarCategoria(nome);
        if (categoriaRepository.findByNomeIgnoreCase(categoria).isPresent()) {
            throw new BusinessException("Categoria ja cadastrada.");
        }
        categoriaRepository.save(new Categoria(categoria));
    }

    @Transactional
    public void atualizarCategoria(String nomeAtual, String novoNome) {
        String atual = normalizarCategoria(nomeAtual);
        String novo = normalizarCategoria(novoNome);

        categoriaRepository.findByNomeIgnoreCase(novo)
                .filter(categoria -> !categoria.getNome().equalsIgnoreCase(atual))
                .ifPresent(categoria -> { throw new BusinessException("Categoria ja cadastrada."); });

        Categoria categoria = categoriaRepository.findByNomeIgnoreCase(atual)
                .orElseGet(() -> new Categoria(atual));
        categoria.setNome(novo);
        categoriaRepository.save(categoria);

        repository.findAll().stream()
                .filter(produto -> produto.getCategoria() != null && produto.getCategoria().equalsIgnoreCase(atual))
                .forEach(produto -> produto.setCategoria(novo));
    }

    @Transactional
    public void excluirCategoria(String nome) {
        String categoria = normalizarCategoria(nome);
        boolean possuiProdutos = repository.findAll().stream()
                .anyMatch(produto -> produto.getCategoria() != null && produto.getCategoria().equalsIgnoreCase(categoria));
        if (possuiProdutos) {
            throw new BusinessException("Nao e possivel excluir categoria vinculada a produtos.");
        }

        Categoria categoriaEncontrada = categoriaRepository.findByNomeIgnoreCase(categoria)
                .orElseThrow(() -> new NotFoundException("Categoria nao encontrada."));
        categoriaRepository.delete(categoriaEncontrada);
    }

    private void registrarCategoriaSeNecessario(String nome) {
        String categoria = normalizarCategoria(nome);
        if (categoriaRepository.findByNomeIgnoreCase(categoria).isEmpty()) {
            categoriaRepository.save(new Categoria(categoria));
        }
    }

    private String normalizarCategoria(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new BusinessException("Informe uma categoria.");
        }
        return nome.trim();
    }

    private Long proximoIdDisponivel() {
        Set<Long> idsUsados = repository.findAll().stream()
                .map(Produto::getId)
                .collect(Collectors.toSet());

        long proximo = 1L;
        while (idsUsados.contains(proximo)) {
            proximo++;
        }
        return proximo;
    }
}
