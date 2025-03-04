package com.biblioteca.gestao_biblioteca.utils;

import java.util.Random;

public class ISBNGenerator {

    public static String generateISBN10() {
        Random random = new Random();
        int[] digits = new int[9];

        for (int i = 0; i < 9; i++) {
            digits[i] = random.nextInt(10);
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += digits[i] * (10 - i);
        }
        int checksum = 11 - (sum % 11);

        String checkDigit = (checksum == 10) ? "X" : (checksum == 11) ? "0" : String.valueOf(checksum);

        StringBuilder isbn = new StringBuilder();
        for (int digit : digits) {
            isbn.append(digit);
        }
        isbn.append(checkDigit);

        return isbn.toString();
    }
}
