package com.biblioteca.gestao_biblioteca.repository;

import com.biblioteca.gestao_biblioteca.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
