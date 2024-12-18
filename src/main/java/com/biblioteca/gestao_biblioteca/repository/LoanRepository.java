package com.biblioteca.gestao_biblioteca.repository;

import com.biblioteca.gestao_biblioteca.enums.Status;
import com.biblioteca.gestao_biblioteca.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, String> {
    List<Loan> findByStatus(Status status);

    boolean existsByUserIdAndStatus(String id, Status status);

    boolean existsByUserIdAndBookIdAndStatus(String id, String bookId, Status status);
}
