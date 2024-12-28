package com.biblioteca.gestao_biblioteca.repository;

import com.biblioteca.gestao_biblioteca.models.SimulatedEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulatedEmailRepository extends JpaRepository<SimulatedEmail, String> {
}
