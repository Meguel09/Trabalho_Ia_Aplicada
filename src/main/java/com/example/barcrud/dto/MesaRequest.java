package com.example.barcrud.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MesaRequest(
        @NotNull(message = "O número da mesa é obrigatório.")
        @Positive(message = "O número da mesa deve ser maior que zero.")
        Integer numero
) {}
