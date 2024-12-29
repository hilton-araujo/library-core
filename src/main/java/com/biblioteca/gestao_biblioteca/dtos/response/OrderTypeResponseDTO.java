package com.biblioteca.gestao_biblioteca.dtos.response;

import com.biblioteca.gestao_biblioteca.enums.OrderTypeEnum;

public record OrderTypeResponseDTO(

        String id,

        String code,

        String designation,

        OrderTypeEnum orderTypes
) {
}
