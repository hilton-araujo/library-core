package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.dtos.request.CreateCategoryDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.CategoryDetailsDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.GeneroDTO;
import com.biblioteca.gestao_biblioteca.functions.GeneratorCode;
import com.biblioteca.gestao_biblioteca.models.Category;
import com.biblioteca.gestao_biblioteca.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private GeneroRepository repository;

    public Category create(Category genero) {
        return repository.save(genero);
    }

    public void registrarGenero(CreateCategoryDTO dto) {
        try {
            Category genero;
            boolean existByGenero = repository.existsByCategory(dto.category());
            if (existByGenero) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Genero com o nome " + dto.category() + " já existe");
            }

            genero = new Category();
            genero.setCategory(dto.category());
            genero.setCode(GeneratorCode.generateCode());

            create(genero);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar genero", e);
        }
    }

    public void atualizarGenero(GeneroDTO dto) {
        try {
            Category category = repository.findByCode(dto.code())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

            if (!category.getCategory().equals(dto.category())) {
                boolean existByCategory = repository.existsByCategory(dto.category());
                if (existByCategory) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Genero com o nome " + dto.category() + " já existe");
                }

                category.setCategory(dto.category());
            }

           create(category);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar genero", e);
        }
    }


    public List<GeneroDTO> listar(){
        List<Category> categories = repository.findAll();
        List<GeneroDTO> dtos = new ArrayList<>();

        for (Category category : categories) {
            GeneroDTO dto = new GeneroDTO(
                    category.getCode(),
                    category.getCategory()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    public CategoryDetailsDTO listarPorCode (String code) {
        Category category = repository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrado"));

        return new CategoryDetailsDTO(
                category.getCode(),
                category.getCategory()
        );
    }
}
