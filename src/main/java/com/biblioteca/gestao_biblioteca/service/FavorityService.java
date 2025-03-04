package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.dtos.response.ListFavoriteDTO;
import com.biblioteca.gestao_biblioteca.dtos.request.ValueDTO;
import com.biblioteca.gestao_biblioteca.utils.GeneratorCode;
import com.biblioteca.gestao_biblioteca.models.Book;
import com.biblioteca.gestao_biblioteca.models.Favorites;
import com.biblioteca.gestao_biblioteca.repository.BookRepository;
import com.biblioteca.gestao_biblioteca.repository.FavorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavorityService {

    @Autowired
    private FavorityRepository repository;

    @Autowired
    private BookRepository bookRepository;

    public Favorites create(Favorites favorites) {
        return repository.save(favorites);
    }

    public void registrarFavorito(ValueDTO dto) {
        try {

            if (dto == null || dto.bookId() == null || dto.bookId().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos");
            }

            Book book = bookRepository.findById(dto.bookId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

            boolean existsByBookId = repository.existsByBookId(dto.bookId());
            if (existsByBookId) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Livro já está nos favoritos");
            }

            Favorites favorites = new Favorites();
            favorites.setBook(book);
            favorites.setCode(GeneratorCode.generateCode());
            create(favorites);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro interno do servidor. Por favor, tente novamente mais tarde!");
        }
    }

    public List<ListFavoriteDTO> listarFavorito(){
        List<Favorites> favorites = repository.findAll();

        List<ListFavoriteDTO> dto = new ArrayList<>();

        for (Favorites favorites1 : favorites){
            ListFavoriteDTO favoriteDTO = new ListFavoriteDTO(
                    favorites1.getId(),
                    favorites1.getCode()
            );
            dto.add(favoriteDTO);
        }
        return dto;
    }
}

