package com.biblioteca.gestao_biblioteca.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentDTO(
        @NotBlank(message = "O comentário é obrigatório")
        @Size(max = 500, message = "O comentário não pode exceder 500 caracteres")
        String comment,

        @NotBlank(message = "O livro é obrigatório")
        String bookId,

        @NotBlank(message = "O cliente é obrigatório")
        String clientId
) {
}