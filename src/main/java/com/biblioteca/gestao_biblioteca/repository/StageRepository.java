package com.biblioteca.gestao_biblioteca.repository;

import com.biblioteca.gestao_biblioteca.models.Stage;
import com.biblioteca.gestao_biblioteca.models.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StageRepository extends JpaRepository<Stage, String> {
    Optional<Stage> findByCode(String s);

    List<Stage> findByWorkflow(Workflow workflow);
}
