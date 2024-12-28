package com.biblioteca.gestao_biblioteca.dtos.request;

import com.biblioteca.gestao_biblioteca.enums.OrderTypeEnum;

public record OrderTypeRequest(

        String designation,

        OrderTypeEnum orderTypes
) {
}
