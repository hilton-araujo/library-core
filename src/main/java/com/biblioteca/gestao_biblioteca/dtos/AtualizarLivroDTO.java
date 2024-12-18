package com.biblioteca.gestao_biblioteca.dtos;

public record AtualizarLivroDTO(

        String id,

        String titulo,

        String autor,

        String generoId,

        int quantidadeDisponível,

        String description,

        String image,

        int publishYear,

        double pageCount,

        double rating
) {
}
