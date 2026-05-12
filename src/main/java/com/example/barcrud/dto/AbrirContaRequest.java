package com.example.barcrud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AbrirContaRequest(
        @NotNull Long mesaId,
        @NotBlank String cliente,
        Boolean individual,
        Boolean taxaServico
) {}