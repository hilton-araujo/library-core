package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.dtos.CreateWishDTO;
import com.biblioteca.gestao_biblioteca.dtos.Response.WishBookDTO;
import com.biblioteca.gestao_biblioteca.models.Book;
import com.biblioteca.gestao_biblioteca.models.Wish;
import com.biblioteca.gestao_biblioteca.repository.BookRepository;
import com.biblioteca.gestao_biblioteca.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishService {

    @Autowired
    private WishRepository repository;

    @Autowired
    private BookRepository bookRepository;

    public Wish create(Wish wish){
        return repository.save(wish);
    }

    public void criarDesejo(CreateWishDTO dto){
        try {
            Wish wish;

            Book book = bookRepository.findById(dto.bookId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

            wish = new Wish();
            wish.setBook(book);

            create(wish);

        } catch (ResponseStatusException e) {
            throw e;
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar desejo", e);
        }
    }

    public List<WishBookDTO> listar(){
        List<Wish> wishes = repository.findAll();;
        List<WishBookDTO> dtos = new ArrayList<>();

        for (Wish wish : wishes){
            WishBookDTO bookDTO = new WishBookDTO(
                    wish.getId(),
                    wish.getBook().getTitulo()
            );
            dtos.add(bookDTO);
        }
        return dtos;
    }
}
