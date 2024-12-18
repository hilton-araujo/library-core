package com.biblioteca.gestao_biblioteca.dtos;

public record CreateCommentDTO(

        String comment,

        String boolId,

        String clientId
) {
}
