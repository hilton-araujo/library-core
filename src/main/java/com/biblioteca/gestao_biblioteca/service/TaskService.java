package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.dtos.request.TaskRequestDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.EmployeeResponseDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.TaskResponseDTO;
import com.biblioteca.gestao_biblioteca.functions.GeneratorCode;
import com.biblioteca.gestao_biblioteca.models.Book;
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

import java.util.ArrayList;
import java.util.List;

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
            List<Employee> employees = new ArrayList<>();

            Stage stage = stageRepository.findByCode(dto.stageCode())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Etapa não encontrado"));

            for (String employeeCode : dto.employeeCode()) {
                Employee employee = employeeRepository.findByCode(employeeCode)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionario não encontrado: " + employeeCode));

                employees.add(employee);
            }

            task = new Task();
            task.setCode(GeneratorCode.generateCode());
            task.setDesignation(dto.designation());
            task.setStage(stage);
            task.setEmployee(employees);

            create(task);

        } catch (ResponseStatusException e) {
            throw e;
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar tarefa", e);
        }
    }

    public List<TaskResponseDTO> listar(){
        List<Task> tasks = repository.findAll();
        List<TaskResponseDTO> dto = new ArrayList<>();

        for (Task task : tasks){
            List<EmployeeResponseDTO> employees = task.getEmployee().stream()
                    .map(employee -> new EmployeeResponseDTO(
                            employee.getId(),
                            employee.getCode(),
                            employee.getName(),
                            employee.getEmail(),
                            employee.getPosition()
                    ))
                    .toList();

            TaskResponseDTO taskResponseDTO = new TaskResponseDTO(
                    task.getCode(),
                    task.getDesignation(),
                    task.getStage().getDesignation(),
                    employees
            );
            dto.add(taskResponseDTO);
        }
        return dto;
    }
}
