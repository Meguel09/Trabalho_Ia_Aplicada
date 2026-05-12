package com.example.barcrud.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mesas")
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private Integer numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private StatusMesa status = StatusMesa.LIVRE;

    public Mesa() {}

    public Mesa(Integer numero) {
        this.numero = numero;
        this.status = StatusMesa.LIVRE;
    }

    public Long getId() { return id; }
    public Integer getNumero() { return numero; }
    public StatusMesa getStatus() { return status; }

    public void setNumero(Integer numero) { this.numero = numero; }
    public void setStatus(StatusMesa status) { this.status = status; }
}