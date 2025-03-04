package com.biblioteca.gestao_biblioteca.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "Reply")
@Table(name = "reply")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String reply;
    private LocalDateTime replyDate;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client cliente;

    public Reply() {}

    public Reply(String reply, LocalDateTime replyDate, Comment comment, Client cliente) {
        this.reply = reply;
        this.replyDate = replyDate;
        this.comment = comment;
        this.cliente = cliente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public LocalDateTime getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(LocalDateTime replyDate) {
        this.replyDate = replyDate;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Client getCliente() {
        return cliente;
    }

    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }
}
