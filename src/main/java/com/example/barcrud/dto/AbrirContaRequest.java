package com.example.barcrud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AbrirContaRequest(
        @NotNull(message = "O id da mesa é obrigatório.")
        @Positive(message = "O id da mesa deve ser maior que zero.")
        Long mesaId,

        @NotBlank(message = "O nome do cliente é obrigatório.")
        @Size(max = 100, message = "O nome do cliente deve ter no máximo 100 caracteres.")
        String cliente,

        Boolean individual,
        Boolean taxaServico
) {}
