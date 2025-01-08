package com.biblioteca.gestao_biblioteca.dtos.request;

import com.biblioteca.gestao_biblioteca.enums.DocumentType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmployeeRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100, message = "O nome não pode exceder 100 caracteres")
        String name,

        @NotBlank(message = "O sobrenome é obrigatório")
        @Size(max = 100, message = "O sobrenome não pode exceder 100 caracteres")
        String surname,

        @NotBlank(message = "O estado civil é obrigatório")
        String maritalStatus,

        @NotBlank(message = "O tipo de documento é obrigatório")
        DocumentType documentType,

        @NotBlank(message = "O número do documento é obrigatório")
        @Size(max = 20, message = "O número do documento não pode exceder 20 caracteres")
        String documentNumber,

        @NotBlank(message = "O NUIT é obrigatório")
        @Pattern(regexp = "\\d{9}", message = "O NUIT deve conter exatamente 9 dígitos")
        String nuit,

        @NotBlank(message = "O gênero é obrigatório")
        String genre,

        @NotBlank(message = "O número MSIDN é obrigatório")
        @Pattern(regexp = "\\+258\\d{9}", message = "O MSIDN deve seguir o formato +258XXXXXXXXX")
        String msidn,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail deve ser válido")
        String email,

        @NotBlank(message = "O cargo é obrigatório")
        String position,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
        String senha
) {
}
