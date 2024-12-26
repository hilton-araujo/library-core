package com.biblioteca.gestao_biblioteca.dtos.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record ClientCreateRequest(
        @NotBlank(message = "O nome é obrigatório.")
        String name,

        @NotBlank(message = "O sobrenome é obrigatório.")
        String surname,

        @NotBlank(message = "O nuit é obrigatório.")
        @Pattern(regexp = "\\d{9}", message = "O nuit deve conter entre 9 dígitos.")
        String nuit,

        @Email(message = "O email deve ser válido.")
        @NotBlank(message = "O email é obrigatório.")
        String email,

        @NotBlank(message = "O telefone é obrigatório.")
        @Pattern(regexp = "\\d{9}", message = "O telefone deve conter 9 dígitos.")
        String phone,

        @NotBlank(message = "O endereço é obrigatório.")
        String address,

        @NotBlank(message = "A cidade é obrigatória.")
        String city,

        @NotBlank(message = "O CEP é obrigatório.")
        @Pattern(regexp = "\\d{8}", message = "O CEP deve conter 8 dígitos.")
        String postalCode,

        @NotBlank(message = "O número do documento é obrigatório.")
        @Size(max = 20, message = "O número do documento não deve exceder 20 caracteres.")
        String documentNumber,

        @NotBlank(message = "A senha é obrigatório.")
        String senha
) {}
