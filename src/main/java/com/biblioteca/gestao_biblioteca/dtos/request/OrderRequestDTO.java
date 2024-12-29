package com.biblioteca.gestao_biblioteca.dtos.request;

import java.util.List;

public record OrderRequestDTO(

        String designation,

        String description,

        String clientCode,

        String orderTypeCode,

        List<String> bookCodes
) {
}
