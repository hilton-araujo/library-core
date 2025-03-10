package com.biblioteca.gestao_biblioteca.enums;

import lombok.Getter;

@Getter
public enum Gender {

    MASCULINO("Masculino"),
    FEMININO("Femininno");

    private final String value;

    Gender(String value) {
        this.value = value;
    }
}
