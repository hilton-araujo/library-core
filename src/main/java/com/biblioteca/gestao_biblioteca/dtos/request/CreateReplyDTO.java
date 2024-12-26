package com.biblioteca.gestao_biblioteca.dtos.request;

public record CreateReplyDTO(

        String reply,

        String commentId,

        String clientId
) {
}
