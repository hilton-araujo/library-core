package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.dtos.request.CreateCommentDTO;
import com.biblioteca.gestao_biblioteca.dtos.request.CreateReplyDTO;
import com.biblioteca.gestao_biblioteca.models.Book;
import com.biblioteca.gestao_biblioteca.models.Client;
import com.biblioteca.gestao_biblioteca.models.Comment;
import com.biblioteca.gestao_biblioteca.models.Reply;
import com.biblioteca.gestao_biblioteca.repository.BookRepository;
import com.biblioteca.gestao_biblioteca.repository.ClientRepository;
import com.biblioteca.gestao_biblioteca.repository.CommentRepository;
import com.biblioteca.gestao_biblioteca.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ReplyRepository replyRepository;

    public Comment create(Comment comment){
        return repository.save(comment);
    }

    public Reply createReply(Reply reply){
        return replyRepository.save(reply);
    }

    public void registarComentario(CreateCommentDTO dto){
        try {
            Comment comment;

            Book book = bookRepository.findById(dto.boolId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

            Client cliente = clientRepository.findById(dto.clientId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));

            comment = new Comment();
            comment.setComment(dto.comment());
            comment.setBook(book);
            comment.setCliente(cliente);
            comment.setCommentDate(LocalDateTime.parse(LocalDateTime.now().toString()));

            create(comment);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar comentario", e);
        }
    }

    public void registarResposta(CreateReplyDTO dto){
        try {
            Reply reply;

            Comment comment = repository.findById(dto.commentId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentario não encontrado"));

            Client cliente = clientRepository.findById(dto.clientId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));

            reply = new Reply();
            reply.setReply(dto.reply());
            reply.setComment(comment);
            reply.setCliente(cliente);
            reply.setReplyDate(LocalDateTime.parse(LocalDateTime.now().toString()));

            createReply(reply);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar resposta no comentario", e);
        }
    }
}
