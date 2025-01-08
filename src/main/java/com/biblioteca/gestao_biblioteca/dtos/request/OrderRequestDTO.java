package com.biblioteca.gestao_biblioteca.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public record OrderRequestDTO(

        @NotBlank(message = "A designação é obrigatória")
        @Size(max = 100, message = "A designação não pode exceder 100 caracteres")
        String designation,

        String description,

        @NotBlank(message = "O cliente é obrigatório")
        String clientCode,

        @NotBlank(message = "O tipo de pedido é obrigatório")
        String orderTypeCode,

        @NotEmpty(message = "A lista  de livros não pode ser vazia")
        List<@NotBlank(message = "O campo de livro não pode ser vazio") String> bookCode
) {
}
