package com.example.barcrud.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "pagamentos")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="conta_id")
    @JsonIgnoreProperties({"pedidos", "pagamentos"})
    private Conta conta;

    @Column(nullable=false)
    private String forma;

    @Column(nullable=false, precision=10, scale=2)
    private BigDecimal valor;

    @Column(nullable=false)
    private LocalDateTime pagoEm = LocalDateTime.now();

    public Pagamento() {}

    public Pagamento(Conta conta, String forma, BigDecimal valor) {
        this.conta = conta;
        this.forma = forma;
        this.valor = valor;
    }

    public Long getId() { return id; }
    public Conta getConta() { return conta; }
    public String getForma() { return forma; }
    public BigDecimal getValor() { return valor; }
    public LocalDateTime getPagoEm() { return pagoEm; }

    public void setConta(Conta conta) { this.conta = conta; }
    public void setForma(String forma) { this.forma = forma; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
}