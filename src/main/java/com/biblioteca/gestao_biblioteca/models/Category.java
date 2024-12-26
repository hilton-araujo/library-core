package com.biblioteca.gestao_biblioteca.models;


import com.biblioteca.gestao_biblioteca.dtos.request.CreateGeneroDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Category")
@Table(name = "category")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String code;

    private String category;

    public Category(CreateGeneroDTO dto) {
        this.category = dto.category();
    }
}
