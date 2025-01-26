package com.biblioteca.gestao_biblioteca.dtos.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record AuthDTO (

//        @NotBlank(message = "O nome de usuário é obrigatório.")
        String username,

//        @NotBlank(message = "A senha é obrigatória.")
        String password

) {
}
