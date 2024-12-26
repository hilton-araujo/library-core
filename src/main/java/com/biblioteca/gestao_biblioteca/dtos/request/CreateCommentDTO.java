package com.biblioteca.gestao_biblioteca.dtos.request;

public record CreateCommentDTO(

        String comment,

        String boolId,

        String clientId
) {
}
