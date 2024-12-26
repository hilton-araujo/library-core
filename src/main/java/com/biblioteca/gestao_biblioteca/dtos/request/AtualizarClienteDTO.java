package com.biblioteca.gestao_biblioteca.dtos.request;

public record AtualizarClienteDTO(

        String code,

        String name,

        String surname,

        String nuit,

        String documentNumber,

        String email,

        String phone,

        String address,

        String city,

        String postalCode

) {
}
