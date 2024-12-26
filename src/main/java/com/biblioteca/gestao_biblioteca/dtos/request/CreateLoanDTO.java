package com.biblioteca.gestao_biblioteca.dtos.request;

public record CreateLoanDTO(
        String userId,

        String bookId,

        String dataDevolucao
) {
}
