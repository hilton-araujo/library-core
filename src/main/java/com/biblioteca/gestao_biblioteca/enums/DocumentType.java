package com.biblioteca.gestao_biblioteca.enums;

import lombok.Getter;

@Getter
public enum DocumentType {
    BI("Bilhete de Identidade"),
    PASSPORT("Passaporte"),
    DIRE("Documento de Identificação de Residente Estrangeiro"),
    NUIT("Número Único de Identificação Tributária"),
    DRIVER_LICENSE("Carta de Condução"),
    ELECTORAL_CARD("Cartão de Eleitor"),
    STUDENT_CARD("Cartão de Estudante"),
    WORK_PERMIT("Permissão de Trabalho"),
    OTHER("Outro");

    private final String value;

    DocumentType(String value) {
        this.value = value;
    }
}
