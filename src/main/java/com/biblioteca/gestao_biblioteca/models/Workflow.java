package com.biblioteca.gestao_biblioteca.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Workflow")
@Table(name = "workflow")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Workflow {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String code;

    @Column(nullable = false)
    private String designation;

    private String descricao;

    @JoinTable(name = "order_type_code")
    private OrderType orderType;
}