package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.dtos.CreateLoanDTO;
import com.biblioteca.gestao_biblioteca.dtos.Response.LoanDTO;
import com.biblioteca.gestao_biblioteca.enums.Status;
import com.biblioteca.gestao_biblioteca.models.Book;
import com.biblioteca.gestao_biblioteca.models.Cliente;
import com.biblioteca.gestao_biblioteca.models.Loan;
import com.biblioteca.gestao_biblioteca.repository.BookRepository;
import com.biblioteca.gestao_biblioteca.repository.ClientRepository;
import com.biblioteca.gestao_biblioteca.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Loan create(Loan loan){
        return repository.save(loan);
    }

    public void registrarEmprestimo(CreateLoanDTO dto){
        try {
            Loan loan;

            Book book = validarLivro(dto.bookId());
            Cliente cliente = validarCliente(dto.userId());

            verificarRegrasDeEmprestimo(cliente, book, dto.bookId());
            atualizarQuantidadeLivro(book);

            loan = new Loan();
            loan.setUser(cliente);
            loan.setBook(book);
            loan.setDataEmprestimo(LocalDateTime.parse(LocalDateTime.now().toString()));
            loan.setDataDevolucao(dto.dataDevolucao());
            loan.setStatus(Status.EM_ABERTO);

            create(loan);

        } catch (ResponseStatusException e) {
            throw e;
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar emprestimo", e);
        }
    }

    public List<LoanDTO> listar(){
        List<Loan> loans = repository.findAll();
        List<LoanDTO> dtos = new ArrayList<>();

        for (Loan loan : loans){
            LoanDTO loanDTO = new LoanDTO(
                    loan.getId(),
                    loan.getUser().getName(),
                    loan.getBook().getTitulo(),
                    loan.getDataEmprestimo(),
                    loan.getDataDevolucao(),
                    loan.getStatus()
            );
            dtos.add(loanDTO);
        }
        return dtos;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void verificarEmprestimosExpirados() {
        LocalDate hoje = LocalDate.now();

        List<Loan> emprestimos = repository.findByStatus(Status.EM_ABERTO);

        for (Loan emprestimo : emprestimos) {
            LocalDate dataDevolucao = LocalDate.parse(emprestimo.getDataDevolucao());

            if (dataDevolucao.isBefore(hoje)) {
                emprestimo.setStatus(Status.ATRASADO);
                repository.save(emprestimo);
            }
        }
    }

    private Book validarLivro(String bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Livro com id " + bookId + " não encontrado"));
    }

    private Cliente validarCliente(String userId) {
        return clientRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente com id " + userId + " não encontrado"));
    }

    private void verificarRegrasDeEmprestimo(Cliente cliente, Book book, String bookId) {
        if (repository.existsByUserIdAndStatus(cliente.getId(), Status.ATRASADO)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O cliente possui empréstimos atrasados e não pode realizar novos empréstimos");
        }

        if (repository.existsByUserIdAndBookIdAndStatus(cliente.getId(), bookId, Status.EM_ABERTO)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O cliente já possui um empréstimo em aberto para este livro");
        }

        if (book.getQuantidadeDisponível() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Não há exemplares disponíveis para empréstimo");
        }
    }

    private void atualizarQuantidadeLivro(Book book) {
        book.setQuantidadeDisponível(book.getQuantidadeDisponível() - 1);
        bookRepository.save(book);
    }


}
