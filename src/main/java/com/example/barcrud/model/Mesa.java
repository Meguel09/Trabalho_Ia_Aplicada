package com.example.barcrud.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mesas")
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private StatusMesa status = StatusMesa.LIVRE;

    public Mesa() {}

    public Mesa(String nome) {
        this.nome = nome;
        this.status = StatusMesa.LIVRE;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public StatusMesa getStatus() { return status; }

    public void setNome(String nome) { this.nome = nome; }
    public void setStatus(StatusMesa status) { this.status = status; }
}
