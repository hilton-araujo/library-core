package com.biblioteca.gestao_biblioteca.dtos;

public record CreateBookDTO(

        String titulo,

        String autor,

        String description,

        String generoId,

        int publishYear,

        double pageCount,

        double rating,

        int quantidadeDisponível,

        String image,

        boolean isHighlighted

) {
}
