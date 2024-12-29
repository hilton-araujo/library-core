package com.biblioteca.gestao_biblioteca.repository;

import com.biblioteca.gestao_biblioteca.models.OrderType;
import com.biblioteca.gestao_biblioteca.models.Stage;
import com.biblioteca.gestao_biblioteca.models.Task;
import com.biblioteca.gestao_biblioteca.models.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    List<Task> findByStageIn(List<Stage> stages);
}
