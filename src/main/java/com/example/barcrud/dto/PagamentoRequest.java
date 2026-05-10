package com.example.barcrud.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record PagamentoRequest(
        @NotBlank(message = "A forma de pagamento é obrigatória.")
        @Size(max = 50, message = "A forma de pagamento deve ter no máximo 50 caracteres.")
        String forma,

        @NotNull(message = "O valor do pagamento é obrigatório.")
        @DecimalMin(value = "0.01", message = "O valor do pagamento deve ser maior que zero.")
        BigDecimal valor
) {}
