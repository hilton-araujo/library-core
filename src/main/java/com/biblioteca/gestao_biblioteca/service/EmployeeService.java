package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.dtos.request.EmployeeRequestDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.EmployeeResponseDTO;
import com.biblioteca.gestao_biblioteca.enums.Papel;
import com.biblioteca.gestao_biblioteca.functions.GeneratorCode;
import com.biblioteca.gestao_biblioteca.models.Auth;
import com.biblioteca.gestao_biblioteca.models.Employee;
import com.biblioteca.gestao_biblioteca.repository.AuthRepository;
import com.biblioteca.gestao_biblioteca.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthRepository authRepository;

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

            String encryptedPassword = passwordEncoder.encode(dto.senha());
            Auth auth = new Auth(dto.email(), encryptedPassword, Papel.FUNCIONARIO);

            employee = new Employee();
            employee.setCode(GeneratorCode.generateCode());
            employee.setName(dto.name());
            employee.setEmail(dto.email());
            employee.setPosition(dto.position());
            employee.setSenha(encryptedPassword);

            authRepository.save(auth);
            create(employee);

        } catch (ResponseStatusException e) {
            throw e;
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar funcionario", e);
        }
    }

    public List<EmployeeResponseDTO> listar(){
        List<Employee> employees = repository.findAll();
        List<EmployeeResponseDTO> dto = new ArrayList<>();

        for (Employee employee : employees){
            EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(
                    employee.getId(),
                    employee.getCode(),
                    employee.getName(),
                    employee.getEmail(),
                    employee.getPosition()
            );
            dto.add(employeeResponseDTO);
        }
        return dto;
    }
}
