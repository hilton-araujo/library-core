package com.biblioteca.gestao_biblioteca.dtos.request;


import jakarta.validation.constraints.NotEmpty;

public record AuthDTO (

        @NotEmpty(message = "O nome de usuário é obrigatório.")
        String username,

        @NotEmpty(message = "A senha é obrigatória.")
        String password

) {
}
