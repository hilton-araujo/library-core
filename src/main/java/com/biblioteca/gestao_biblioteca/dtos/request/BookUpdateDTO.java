package com.biblioteca.gestao_biblioteca.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record BookUpdateDTO(

        @NotNull(message = "O código do livro é obrigatório")
        @NotBlank(message = "O código do livro não pode estar em branco")
        String code,

        @NotNull(message = "O título do livro é obrigatório")
        @NotBlank(message = "O título do livro não pode estar em branco")
        @Size(max = 255, message = "O título não pode ter mais de 255 caracteres")
        String title,

        @NotNull(message = "O autor do livro é obrigatório")
        @NotBlank(message = "O autor do livro não pode estar em branco")
        String author,

        @NotBlank(message = "A editora é obrigatória")
        String publisher,

        @NotBlank(message = "O idioma é obrigatório")
        String language,

        @NotBlank(message = "A localização é obrigatória")
        String location,

        String description,

        @NotNull(message = "A quantidade disponível é obrigatória")
        @Positive(message = "A quantidade disponível deve ser maior que zero")
        Integer availableQuantity,

        @Positive(message = "O número de páginas deve ser maior que zero")
        Integer pageCount,

        @Positive(message = "O ano de publicação deve ser maior que zero")
        Integer publishYear,

        @Positive(message = "A avaliação deve ser maior que zero")
        Integer rating,

        @NotBlank(message = "A categoria é obrigatório")
        String categoryCode,

        String image
) {
}
