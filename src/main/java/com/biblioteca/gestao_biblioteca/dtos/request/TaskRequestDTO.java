package com.biblioteca.gestao_biblioteca.dtos.request;

import java.util.List;

public record TaskRequestDTO(

        String designation,

        String stageCode,

        List<String> employeeCode
) {
}
