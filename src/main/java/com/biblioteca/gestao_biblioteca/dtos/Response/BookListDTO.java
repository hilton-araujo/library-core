package com.biblioteca.gestao_biblioteca.dtos.Response;

public record BookListDTO(

        String id,

        String title,

        String author,

        String genre,

        int availableQuantity,

        String description,

        String image,

        int publishYear,

        double pageCount,

        double rating,

        boolean favority
) {
}
