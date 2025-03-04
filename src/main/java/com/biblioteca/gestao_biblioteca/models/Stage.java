package com.biblioteca.gestao_biblioteca.models;

import jakarta.persistence.*;

@Entity(name = "Stage")
@Table(name = "stage")
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String code;

    @Column(nullable = false)
    private String designation;

    @Column(nullable = false)
    private Integer stageOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id", nullable = false)
    private Workflow workflow;

    public Stage() {}

    public Stage(String code, String designation, Integer stageOrder, Workflow workflow) {
        this.code = code;
        this.designation = designation;
        this.stageOrder = stageOrder;
        this.workflow = workflow;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getStageOrder() {
        return stageOrder;
    }

    public void setStageOrder(Integer stageOrder) {
        this.stageOrder = stageOrder;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    @Override
    public String toString() {
        return "Stage{" +
                "id=" + id +
                ", designation='" + designation + '\'' +
                ", workflow=" + workflow.getDesignation() +
                '}';
    }
}
