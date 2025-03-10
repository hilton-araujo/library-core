package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.dtos.request.BookInteractionRequestDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.ListFavoriteDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.WishBookDTO;
import com.biblioteca.gestao_biblioteca.models.Book;
import com.biblioteca.gestao_biblioteca.models.Client;
import com.biblioteca.gestao_biblioteca.models.Favorite;
import com.biblioteca.gestao_biblioteca.models.Wish;
import com.biblioteca.gestao_biblioteca.repository.BookRepository;
import com.biblioteca.gestao_biblioteca.repository.ClientRepository;
import com.biblioteca.gestao_biblioteca.repository.FavorityRepository;
import com.biblioteca.gestao_biblioteca.repository.WishRepository;
import com.biblioteca.gestao_biblioteca.utils.GeneratorCode;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BookInteractionService {
//    private static final Logger logger = LoggerFactory.getLogger(BookInteractionService.class);
//
//    private final WishRepository wishRepository;
//    private final BookRepository bookRepository;
//    private final ClientRepository clientRepository;
//    private final FavorityRepository favorityRepository;
//
//    public BookInteractionService(WishRepository wishRepository,
//                                  BookRepository bookRepository,
//                                  ClientRepository clientRepository, FavorityRepository favorityRepository) {
//        this.wishRepository = wishRepository;
//        this.bookRepository = bookRepository;
//        this.clientRepository = clientRepository;
//        this.favorityRepository = favorityRepository;
//    }
//
//    public Wish createWish(Wish wish){
//        return wishRepository.save(wish);
//    }
//
//    public Favorite createFavoritie(Favorite favorites) {
//        return favorityRepository.save(favorites);
//    }
//
//    @Transactional
//    public void createWish(BookInteractionRequestDTO dto) {
//        try {
//            validateRequest(dto);
//
//            Book book = findBookByCode(dto.bookCode());
//            Client client = findClientByCode(dto.clientCode());
//
//            Wish wish = new Wish();
//            wish.setBook(book);
//            wish.setClient(client);
//
//            this.createWish(wish);
//
//        } catch (ResponseStatusException e) {
//            throw e;
//        } catch (Exception e) {
//            logger.error("Error creating wish", e);
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
//                    "Error registering wish. Please try again later.", e);
//        }
//    }
//
//    @Transactional
//    public void addFavorite(BookInteractionRequestDTO dto) {
//        try {
//            validateRequest(dto);
//
//            Book book = findBookByCode(dto.bookCode());
//            Client client = findClientByCode(dto.clientCode());
//
//            if (favorityRepository.existsByBookCode(dto.bookCode())) {
//                throw new ResponseStatusException(HttpStatus.CONFLICT, "Livro já está nos favoritos");
//            }
//
//            Favorite favorite = new Favorite();
//            favorite.setCode(GeneratorCode.generateCode());
//            favorite.setBook(book);
//            favorite.setClient(client);
//
//            this.createFavoritie(favorite);
//
//        } catch (ResponseStatusException e) {
//            throw e;
//        } catch (Exception e) {
//            logger.error("Error adding favorite", e);
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
//                    "Internal server error. Please try again later.");
//        }
//    }
//
//    public List<WishBookDTO> listar(){
//        List<Wish> wishes = wishRepository.findAll();;
//        List<WishBookDTO> dtos = new ArrayList<>();
//
//        for (Wish wish : wishes){
//            WishBookDTO bookDTO = new WishBookDTO(
//                    wish.getId(),
//                    wish.getBook().getTitle()
//            );
//            dtos.add(bookDTO);
//        }
//        return dtos;
//    }
//
//    public List<ListFavoriteDTO> listarFavorito(){
//        List<Favorite> favorites = favorityRepository.findAll();
//
//        List<ListFavoriteDTO> dto = new ArrayList<>();
//
//        for (Favorite favorites1 : favorites){
//            ListFavoriteDTO favoriteDTO = new ListFavoriteDTO(
//                    favorites1.getId(),
//                    favorites1.getCode()
//            );
//            dto.add(favoriteDTO);
//        }
//        return dto;
//    }
//
//    private void validateRequest(BookInteractionRequestDTO dto) {
//        if (dto == null ||
//                Objects.isNull(dto.bookCode()) || dto.bookCode().isEmpty() ||
//                Objects.isNull(dto.clientCode()) || dto.clientCode().isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data");
//        }
//    }
//
//    private Book findBookByCode(String bookCode) {
//        return bookRepository.findByCode(bookCode)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
//    }
//
//    private Client findClientByCode(String clientCode) {
//        return clientRepository.findByCode(clientCode)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
//    }
}