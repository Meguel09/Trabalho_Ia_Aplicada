package com.example.barcrud.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProdutoRequest(
        @NotBlank(message = "O nome do produto é obrigatório.")
        @Size(max = 100, message = "O nome do produto deve ter no máximo 100 caracteres.")
        String nome,

        @NotBlank(message = "A categoria do produto é obrigatória.")
        @Size(max = 50, message = "A categoria deve ter no máximo 50 caracteres.")
        String categoria,

        @NotNull(message = "O preço do produto é obrigatório.")
        @DecimalMin(value = "0.01", message = "O preço do produto deve ser maior que zero.")
        BigDecimal preco,

        Boolean ativo
) {}
