package com.biblioteca.gestao_biblioteca.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {

    AGUARDANDO_APROVACAO("Aguardando aprovação"),
    PEDIDO_APROVADO("Pedido aprovado"),
    PEDIDO_NEGADO("Pedido negado"),
    PEDIDO_CANCELADO("Pedido cancelado");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }
}