package com.biblioteca.gestao_biblioteca.repository;

import com.biblioteca.gestao_biblioteca.models.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderTypeRepository extends JpaRepository<OrderType, String> {
    boolean existsByDesignation(String designation);

    Optional<OrderType> findByCode(String s);
}
