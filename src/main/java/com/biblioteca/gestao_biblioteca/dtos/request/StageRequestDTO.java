package com.biblioteca.gestao_biblioteca.dtos.request;

public record StageRequestDTO(

        String code,

        String designation,

        Integer stageOrder,

        String workflow_code
) {
}
