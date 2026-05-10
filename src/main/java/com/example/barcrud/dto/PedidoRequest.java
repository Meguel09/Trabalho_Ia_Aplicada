package com.example.barcrud.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PedidoRequest(
        @NotNull(message = "O id da conta é obrigatório.")
        @Positive(message = "O id da conta deve ser maior que zero.")
        Long contaId
) {}
