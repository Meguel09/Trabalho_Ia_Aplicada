package com.example.barcrud.dto;

import com.example.barcrud.model.StatusMesa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MesaRequest(
    @NotBlank @Size(max = 60) String nome,
    StatusMesa status
) {}
