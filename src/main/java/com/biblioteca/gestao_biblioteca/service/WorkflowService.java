package com.biblioteca.gestao_biblioteca.service;


import com.biblioteca.gestao_biblioteca.dtos.request.WorkflowRequestDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.WorkflowResponseDTO;
import com.biblioteca.gestao_biblioteca.functions.GeneratorCode;
import com.biblioteca.gestao_biblioteca.models.OrderType;
import com.biblioteca.gestao_biblioteca.models.Workflow;
import com.biblioteca.gestao_biblioteca.repository.OrderTypeRepository;
import com.biblioteca.gestao_biblioteca.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkflowService {

    @Autowired
    private WorkflowRepository repository;

    @Autowired
    private OrderTypeRepository orderTypeRepository;

    public Workflow create(Workflow workflow) {
        return repository.save(workflow);
    }

    public void criarWorkflow(WorkflowRequestDTO dto) {
        try {
            Workflow workflow;

            OrderType orderType = orderTypeRepository.findByCode(dto.order_type_code())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de pedido não encontrado"));

            boolean existsByDesignation = repository.existsByDesignation(dto.designation());
            if (existsByDesignation){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "WorkFlow com a designação " + dto.designation() + " já existe");
            }

            workflow = new Workflow();
            workflow.setCode(GeneratorCode.generateCode());
            workflow.setDesignation(dto.designation());
            workflow.setOrderType(orderType);

            create(workflow);
        } catch (ResponseStatusException e) {
            throw e;
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar workflow", e);
        }
    }

    public List<WorkflowResponseDTO> listar(){
        List<Workflow> workflows = repository.findAll();
        List<WorkflowResponseDTO> dtos = new ArrayList<>();

        for (Workflow workflow : workflows){
            WorkflowResponseDTO workflowResponseDTO = new WorkflowResponseDTO(
                    workflow.getCode(),
                    workflow.getDesignation(),
                    workflow.getDescricao(),
                    workflow.getOrderType().getDesignation()
            );
            dtos.add(workflowResponseDTO);
        }
        return dtos;
    }
}
