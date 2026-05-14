package com.example.barcrud.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias", uniqueConstraints = @UniqueConstraint(columnNames = "nome"))
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String nome;

    public Categoria() {}

    public Categoria(String nome) {
        this.nome = nome;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }
}
