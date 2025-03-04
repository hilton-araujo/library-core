package com.biblioteca.gestao_biblioteca.models;

import com.biblioteca.gestao_biblioteca.enums.OrderTypeEnum;
import jakarta.persistence.*;

@Entity(name = "Order_Type")
@Table(name = "order_type")
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

    public OrderType() {}

    public OrderType(String code, String designation, OrderTypeEnum orderType) {
        this.code = code;
        this.designation = designation;
        this.orderType = orderType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public OrderTypeEnum getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderTypeEnum orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return "OrderType{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", designation='" + designation + '\'' +
                ", orderType=" + orderType +
                '}';
    }
}