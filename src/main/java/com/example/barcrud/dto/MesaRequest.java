package com.example.barcrud.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MesaRequest(@NotNull @Min(1) Integer numero) {}