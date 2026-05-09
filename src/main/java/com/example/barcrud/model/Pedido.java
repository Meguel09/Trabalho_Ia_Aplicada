package com.example.barcrud.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="conta_id")
    private Conta conta;

    @Column(nullable=false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @OneToMany(mappedBy="pedido", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<ItemPedido> itens = new ArrayList<>();

    public Pedido() {}
    public Pedido(Conta conta) { this.conta = conta; }

    public Long getId() { return id; }
    public Conta getConta() { return conta; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public List<ItemPedido> getItens() { return itens; }

    public void setConta(Conta conta) { this.conta = conta; }
}