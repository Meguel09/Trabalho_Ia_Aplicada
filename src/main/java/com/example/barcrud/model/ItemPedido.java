package com.example.barcrud.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "itens_pedido")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="pedido_id")
    @JsonIgnoreProperties({"itens", "conta"})
    private Pedido pedido;

    @ManyToOne(optional=false)
    @JoinColumn(name="produto_id")
    private Produto produto;

    @Column(nullable=false)
    private Integer quantidade;

    @Column(nullable=false, precision=10, scale=2)
    private BigDecimal precoUnitario;

    public ItemPedido() {}

    public ItemPedido(Pedido pedido, Produto produto, Integer quantidade) {
        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
    }

    public Long getId() { return id; }
    public Pedido getPedido() { return pedido; }
    public Produto getProduto() { return produto; }
    public Integer getQuantidade() { return quantidade; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }

    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public void setProduto(Produto produto) { this.produto = produto; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }

    public BigDecimal getTotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}