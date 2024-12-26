package com.biblioteca.gestao_biblioteca.functions;

import java.util.Random;

public class GeneratorCode {

    private static final int TAMANHO_CODIGO = 7;

    public static String generateCode() {
        Random random = new Random();
        StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < TAMANHO_CODIGO; i++) {
            codigo.append(random.nextInt(10));
        }
        return codigo.toString();
    }
}
