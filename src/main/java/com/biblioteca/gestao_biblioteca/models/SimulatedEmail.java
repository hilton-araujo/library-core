package com.biblioteca.gestao_biblioteca.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity(name = "SimulatedEmail")
@Table(name = "simulated_email")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SimulatedEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String recipientEmail;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false)
    private LocalDateTime sentAt;
}
