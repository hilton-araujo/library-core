package com.biblioteca.gestao_biblioteca.dtos.request;

import jakarta.validation.constraints.*;

public record BookRequestDTO(

        @NotNull(message = "O título é obrigatório.")
        @NotBlank(message = "O título não pode ser vazio.")
        String title,

        @NotNull(message = "O autor é obrigatório.")
        @NotBlank(message = "O autor não pode ser vazio.")
        String author,

        @NotNull(message = "O ano de publicação é obrigatório.")
        @NotBlank(message = "O ano de publicação não pode ser vazio.")
        String publisher,

        String language,

        @NotNull(message = "O local de publicação é obrigatório.")
        @NotBlank(message = "O local de publicação não pode ser vazio.")
        String location,

        String description,

        @NotNull(message = "A quantidade disponível é obrigatória.")
        @Positive(message = "A quantidade disponível deve ser maior que zero.")
        Integer availableQuantity,

        @Min(value = 1, message = "A quantidade de páginas deve ser maior que zero.")
        Integer pageCount,

        @Min(value = 1000, message = "O ano de publicação deve ser um valor válido (a partir de 1000).")
        Integer publishYear,

        @Min(value = 1, message = "A avaliação deve ser maior que zero.")
        @Max(value = 5, message = "A avaliação não pode ser maior que 5.")
        Integer rating,

        @NotNull(message = "A categoris é obrigatório.")
        @NotBlank(message = "A categoris não pode ser vazio.")
        String categoryCode,

        String image
) {
}
