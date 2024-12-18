package com.biblioteca.gestao_biblioteca.dtos.Response;

import java.util.List;

public record BookDTO(
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

        List<CommentDTO> comments
) {
}
