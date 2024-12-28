package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.dtos.request.EmployeeRequestDTO;
import com.biblioteca.gestao_biblioteca.functions.GeneratorCode;
import com.biblioteca.gestao_biblioteca.models.Employee;
import com.biblioteca.gestao_biblioteca.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public Employee create(Employee employee){
        return repository.save(employee);
    }

    public void registrarFuncionario(EmployeeRequestDTO dto){
        try {
            Employee employee;

            boolean existsByEmail = repository.existsByEmail(dto.email());
            if (existsByEmail){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Funcionario com email " +dto.email() +" já existe");
            }

            employee = new Employee();
            employee.setCode(GeneratorCode.generateCode());
            employee.setName(dto.name());
            employee.setEmail(dto.email());
            employee.setPosition(dto.position());

            create(employee);

        } catch (ResponseStatusException e) {
            throw e;
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar funcionario", e);
        }
    }
}
