package com.biblioteca.gestao_biblioteca.dtos.response;

import java.util.List;

public record TaskResponseDTO(

        String code,

        String designation,

        String stage,

        List<EmployeeResponseDTO> employee
) {
}
