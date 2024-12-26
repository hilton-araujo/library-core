package com.biblioteca.gestao_biblioteca.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Client")
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true, length = 9)
    private String nuit;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 9)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false, length = 8)
    private String postalCode;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false, unique = true, length = 20)
    private String documentNumber;

    @Column(nullable = false, unique = true)
    private String senha;
}
