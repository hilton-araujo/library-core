package com.biblioteca.gestao_biblioteca.models;

import com.biblioteca.gestao_biblioteca.enums.OrderTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Order_Type")
@Table(name = "order_type")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String code;

    @Column(nullable = false)
    private String designation;

    @Column(name = "typeOfOrder", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderTypeEnum orderType;

    @Override
    public String toString() {
        return "OrderType{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }

}