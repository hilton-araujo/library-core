package com.biblioteca.gestao_biblioteca.dtos.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record AtualizarClienteDTO(

        @NotEmpty(message = "O código é obrigatório.")
        String code,

        @NotEmpty(message = "O nome é obrigatório.")
        String name,

        @NotEmpty(message = "O sobrenome é obrigatório.")
        String surname,

        @NotEmpty(message = "O NUIT é obrigatório.")
        String nuit,

        @NotEmpty(message = "O número de documento é obrigatório.")
        String documentNumber,

        @Email(message = "O e-mail deve ser válido.")
        @NotEmpty(message = "O e-mail é obrigatório.")
        String email,

        @NotEmpty(message = "O telefone é obrigatório.")
        String phone,

        @NotEmpty(message = "O endereço é obrigatório.")
        String address,

        @NotEmpty(message = "A cidade é obrigatória.")
        String city,

        @NotEmpty(message = "O código postal é obrigatório.")
        String postalCode

) {
}
