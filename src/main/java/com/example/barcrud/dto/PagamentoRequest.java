package com.example.barcrud.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PagamentoRequest(@NotBlank String forma, @NotNull @DecimalMin(value = "0.01") BigDecimal valor) {}