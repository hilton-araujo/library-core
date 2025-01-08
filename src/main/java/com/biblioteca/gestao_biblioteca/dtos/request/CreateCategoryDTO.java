package com.biblioteca.gestao_biblioteca.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCategoryDTO(

        @NotNull(message = "A categoria é obrigatório.")
        @NotBlank(message = "A categoria não pode ser vazio.")
        String category
) {
}
