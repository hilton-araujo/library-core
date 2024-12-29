package com.biblioteca.gestao_biblioteca.dtos.response;

public record EmployeeResponseDTO(

        String id,

        String code,

        String name,

        String email,

        String position
) {
}
