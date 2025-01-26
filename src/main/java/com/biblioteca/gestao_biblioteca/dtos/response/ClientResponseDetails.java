package com.biblioteca.gestao_biblioteca.dtos.response;

import java.util.List;

public record ClientResponseDetails(
        String code,
        String name,
        String surname,
        String nuit,
        String documentNumber,
        String email,
        String phone,
        String address,
        String city,
        String postalCode,
        Boolean active,
        List<OrderResponseDTO> orders
) {
}
