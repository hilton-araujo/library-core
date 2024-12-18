package com.biblioteca.gestao_biblioteca.dtos;

public record CreateLoanDTO(
        String userId,

        String bookId,

        String dataDevolucao
) {
}
