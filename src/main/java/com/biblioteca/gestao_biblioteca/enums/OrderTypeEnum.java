package com.biblioteca.gestao_biblioteca.enums;

import lombok.Getter;

@Getter
public enum OrderTypeEnum {

    ALTERACAO("Alteração"),
    CANCELAMENTO("Cancelamento"),
    COMPRA("Compra"),
    DEVOLUCAO("Devolução"),
    DEVOLUCAO_ATRASADA("Devolução atrasada"),
    EMPRESTIMO("Empréstimo"),
    PAGAMENTO("Pagamento"),
    PERSONALIZACAO("Personalização"),
    REEMBOLSO("Reembolso"),
    RENOVACAO_EMPRESTIMO("Renovação de empréstimo"),
    REPOSICAO("Reposição"),
    RESERVA_ITEM("Reserva de item"),
    RESERVA_SALA("Reserva de sala"),
    SUGESTAO_ITEM("Sugestão de item"),
    SUGESTAO_MELHORIA("Sugestão de melhoria"),
    SUPORTE_TECNICO("Suporte técnico");

    private final String value;

    OrderTypeEnum(String value) {
        this.value = value;
    }
}
