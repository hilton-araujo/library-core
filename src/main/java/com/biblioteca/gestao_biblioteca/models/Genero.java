package com.biblioteca.gestao_biblioteca.models;


import com.biblioteca.gestao_biblioteca.dtos.CreateGeneroDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Gender")
@Table(name = "gender")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String genero;

    public Genero(CreateGeneroDTO dto) {
        this.genero = dto.genero();
    }
}
