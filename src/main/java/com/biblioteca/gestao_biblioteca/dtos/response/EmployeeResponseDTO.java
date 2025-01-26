package com.biblioteca.gestao_biblioteca.dtos.response;

import com.biblioteca.gestao_biblioteca.enums.DocumentType;

public record EmployeeResponseDTO(

        String code,

        String name,

        String surname,

        String genre,

        String msidn,

        String nuit,

        DocumentType documentType,

        String documentNumber,

        String email,

        String position,

        Boolean active
) {
}
