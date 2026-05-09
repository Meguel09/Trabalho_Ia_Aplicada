package com.example.barcrud.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contas")
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="mesa_id")
    private Mesa mesa;

    @Column(nullable=false)
    private String cliente;

    @Column(nullable=false)
    private Boolean individual = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private StatusConta status = StatusConta.ABERTA;

    @Column(nullable=false)
    private Boolean taxaServico = false;

    @Column(nullable=false)
    private LocalDateTime abertaEm = LocalDateTime.now();

    private LocalDateTime fechadaEm;

    @OneToMany(mappedBy="conta", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Pedido> pedidos = new ArrayList<>();

    @OneToMany(mappedBy="conta", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Pagamento> pagamentos = new ArrayList<>();

    public Conta() {}

    public Conta(Mesa mesa, String cliente, Boolean individual, Boolean taxaServico) {
        this.mesa = mesa;
        this.cliente = cliente;
        this.individual = individual;
        this.taxaServico = taxaServico;
    }

    public Long getId() { return id; }
    public Mesa getMesa() { return mesa; }
    public String getCliente() { return cliente; }
    public Boolean getIndividual() { return individual; }
    public StatusConta getStatus() { return status; }
    public Boolean getTaxaServico() { return taxaServico; }
    public LocalDateTime getAbertaEm() { return abertaEm; }
    public LocalDateTime getFechadaEm() { return fechadaEm; }
    public List<Pedido> getPedidos() { return pedidos; }

    public void setMesa(Mesa mesa) { this.mesa = mesa; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public void setIndividual(Boolean individual) { this.individual = individual; }
    public void setStatus(StatusConta status) { this.status = status; }
    public void setTaxaServico(Boolean taxaServico) { this.taxaServico = taxaServico; }
    public void setFechadaEm(LocalDateTime fechadaEm) { this.fechadaEm = fechadaEm; }

    public BigDecimal getSubtotal() {
        return pedidos.stream()
                .flatMap(p -> p.getItens().stream())
                .map(ItemPedido::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getValorTaxaServico() {
        return taxaServico ? getSubtotal().multiply(new BigDecimal("0.10")) : BigDecimal.ZERO;
    }

    public BigDecimal getTotal() {
        return getSubtotal().add(getValorTaxaServico());
    }
}