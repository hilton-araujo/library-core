package com.biblioteca.gestao_biblioteca.repository;

import com.biblioteca.gestao_biblioteca.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    boolean existsByEmail(String email);

    Optional<Employee> findByCode(String s);
}
