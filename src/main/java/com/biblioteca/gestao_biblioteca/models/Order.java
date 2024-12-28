package com.biblioteca.gestao_biblioteca.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "Order")
@Table(name = "order")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String designation;

    private String description;

    @Column(nullable = false)
    private Client clientId;

    @ManyToOne
    @JoinColumn(name = "order_type_id", nullable = false)
    private OrderType orderType;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
