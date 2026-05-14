package com.example.barcrud.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    private Long id;

    @Column(nullable=false)
    private String nome;

    @Column(nullable=false)
    private String categoria;

    @Column(nullable=false, precision=10, scale=2)
    private BigDecimal preco;

    @Column(nullable=false)
    private Boolean ativo = true;

    public Produto() {}

    public Produto(String nome, String categoria, BigDecimal preco) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.ativo = true;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCategoria() { return categoria; }
    public BigDecimal getPreco() { return preco; }
    public Boolean getAtivo() { return ativo; }

    public void setNome(String nome) { this.nome = nome; }
    public void setId(Long id) { this.id = id; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}
