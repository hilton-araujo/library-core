package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.dtos.request.TaskRequestDTO;
import com.biblioteca.gestao_biblioteca.functions.GeneratorCode;
import com.biblioteca.gestao_biblioteca.models.Employee;
import com.biblioteca.gestao_biblioteca.models.Stage;
import com.biblioteca.gestao_biblioteca.models.Task;
import com.biblioteca.gestao_biblioteca.repository.EmployeeRepository;
import com.biblioteca.gestao_biblioteca.repository.StageRepository;
import com.biblioteca.gestao_biblioteca.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private StageRepository stageRepository;

    public Task create(Task task) {
        return repository.save(task);
    }

    public void registrarTarefa(TaskRequestDTO dto) {
        try {
            Task task;

            Stage stage = stageRepository.findByCode(dto.stageCode())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Etapa não encontrado"));

            Employee employee = employeeRepository.findByCode(dto.employeeCode())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionario não encontrado"));

            task = new Task();
            task.setCode(GeneratorCode.generateCode());
            task.setDesignation(dto.designation());
            task.setStage(stage);
            task.setEmployee(employee);

            create(task);

        } catch (ResponseStatusException e) {
            throw e;
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar tarefa", e);
        }
    }
}
