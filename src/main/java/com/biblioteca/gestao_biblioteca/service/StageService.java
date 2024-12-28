package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.dtos.request.StageRequestDTO;
import com.biblioteca.gestao_biblioteca.functions.GeneratorCode;
import com.biblioteca.gestao_biblioteca.models.Stage;
import com.biblioteca.gestao_biblioteca.models.Workflow;
import com.biblioteca.gestao_biblioteca.repository.StageRepository;
import com.biblioteca.gestao_biblioteca.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StageService {

    @Autowired
    private StageRepository repository;

    @Autowired
    private WorkflowRepository workflowRepository;

    public Stage create(Stage stage) {
        return repository.save(stage);
    }

    public void registrarEtapa(StageRequestDTO dto) {
        try {
            Stage stage;

            Workflow workflow = workflowRepository.findByCode(dto.workflow_code())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workflow não encontado"));

            stage = new Stage();
            stage.setCode(GeneratorCode.generateCode());
            stage.setDesignation(dto.designation());
            stage.setStageOrder(dto.stageOrder());
            stage.setWorkflow(workflow);

            create(stage);

        } catch (ResponseStatusException e) {
            throw e;
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar etapa", e);
        }
    }
}
