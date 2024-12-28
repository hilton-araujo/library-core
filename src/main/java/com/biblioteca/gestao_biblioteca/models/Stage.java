package com.biblioteca.gestao_biblioteca.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Stage")
@Table(name = "stage")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
}
