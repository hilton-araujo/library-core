package com.biblioteca.gestao_biblioteca.models;

import jakarta.persistence.*;

@Entity(name = "Favority")
@Table(name = "favority")
public class Favorites {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String code;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public Favorites() {
    }

    public Favorites(String id, String code, Book book) {
        this.id = id;
        this.code = code;
        this.book = book;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
