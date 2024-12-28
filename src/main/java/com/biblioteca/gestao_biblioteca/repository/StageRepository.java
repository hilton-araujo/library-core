package com.biblioteca.gestao_biblioteca.repository;

import com.biblioteca.gestao_biblioteca.models.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StageRepository extends JpaRepository<Stage, String> {
    Optional<Stage> findByCode(String s);
}
