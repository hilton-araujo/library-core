package com.biblioteca.gestao_biblioteca.dtos.response;

public record StageResponseDTO(

        String code,

        String designation,

        Integer stageOrder,

        String workflow
) {
}
