package com.biblioteca.gestao_biblioteca.dtos.response;

public record ClientResponse(

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

        Boolean active

        ) {
}
