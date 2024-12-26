package com.biblioteca.gestao_biblioteca.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookUpdateDTO(

        String code,

        @NotNull
        @NotBlank
        String title,

        @NotNull
        @NotBlank
        String author,

        String isbn,

        String publisher,

        String language,

        String location,

        String description,

        @NotNull
        Integer availableQuantity,

        Integer pageCount,

        Integer publishYear,

        Integer rating,

        String categoryCode,

        String image
) {
}
