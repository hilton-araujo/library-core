package com.biblioteca.gestao_biblioteca.dtos.Response;

import com.biblioteca.gestao_biblioteca.enums.Status;

import java.time.LocalDateTime;

public record LoanDTO(

        String id,

        String client,

        String book,

        LocalDateTime dataEmprestimo,

        String dataDevolucao,

        Status status
) {
}
