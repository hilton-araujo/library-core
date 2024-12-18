package com.biblioteca.gestao_biblioteca.functions;

import java.util.Random;

public class GerarCodigo {

    private static final int TAMANHO_CODIGO = 7;

    public static String gerarCodigo() {
        Random random = new Random();
        StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < TAMANHO_CODIGO; i++) {
            codigo.append(random.nextInt(10));
        }
        return codigo.toString();
    }
}
