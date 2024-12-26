package com.biblioteca.gestao_biblioteca.dtos.response;

import java.util.List;

public record BookDetailsDTO(

        String code,

        String title,

        String author,

        String isbn,

        String publisher,

        String language,

        String location,

        String description,

        Integer availableQuantity,

        Integer pageCount,

        Integer publishYear,

        Integer rating,

        String category,

        String categoryCode,

        String image,

        Boolean active,

        List<LoanDTO> clientLoans,

        List<CommentDTO> comments
) {
}
