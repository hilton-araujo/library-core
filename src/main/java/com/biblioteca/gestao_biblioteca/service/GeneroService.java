package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.dtos.CreateGeneroDTO;
import com.biblioteca.gestao_biblioteca.dtos.Response.GeneroDTO;
import com.biblioteca.gestao_biblioteca.models.Genero;
import com.biblioteca.gestao_biblioteca.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeneroService {

    @Autowired
    private GeneroRepository repository;

    public Genero create(Genero genero) {
        return repository.save(genero);
    }

    public void registrarGenero(CreateGeneroDTO dto) {
        try {
            Genero genero;
            boolean existByGenero = repository.existsByGenero(dto.genero());
            if (existByGenero) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Genero com o nome " + dto.genero() + " já existe");
            }

            genero = new Genero(dto);
            create(genero);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar genero", e);
        }
    }

    public void atualizarGenero(GeneroDTO dto) {
        try {
            Genero genero = repository.findById((dto.id()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genero não encontrado"));

            if (!genero.getGenero().equals(dto.genero())) {
                boolean existByGenero = repository.existsByGenero(dto.genero());
                if (existByGenero) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Genero com o nome " + dto.genero() + " já existe");
                }
            }

            genero = new Genero();
            genero.setId(dto.id());
            genero.setGenero(dto.genero());

            create(genero);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizado genero", e);
        }
    }

    public List<GeneroDTO> listar(){
        List<Genero> generos = repository.findAll();
        List<GeneroDTO> dtos = new ArrayList<>();

        for (Genero genero : generos) {
            GeneroDTO dto = new GeneroDTO(
                    genero.getId(),
                    genero.getGenero()
            );
            dtos.add(dto);
        }
        return dtos;
    }
}
