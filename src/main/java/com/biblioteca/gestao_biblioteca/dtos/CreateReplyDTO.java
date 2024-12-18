package com.biblioteca.gestao_biblioteca.dtos;

public record CreateReplyDTO(

        String reply,

        String commentId,

        String clientId
) {
}
