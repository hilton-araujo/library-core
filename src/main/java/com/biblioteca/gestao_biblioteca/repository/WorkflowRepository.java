package com.biblioteca.gestao_biblioteca.repository;

import com.biblioteca.gestao_biblioteca.models.OrderType;
import com.biblioteca.gestao_biblioteca.models.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkflowRepository extends JpaRepository<Workflow, String> {
    boolean existsByDesignation(String designation);

    Optional<Workflow> findByCode(String s);

    Optional<Workflow> findByOrderType(OrderType orderType);
}
