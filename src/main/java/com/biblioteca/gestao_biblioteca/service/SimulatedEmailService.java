package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.models.SimulatedEmail;
import com.biblioteca.gestao_biblioteca.repository.SimulatedEmailRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SimulatedEmailService {

    private final SimulatedEmailRepository simulatedEmailRepository;

    public SimulatedEmailService(SimulatedEmailRepository simulatedEmailRepository) {
        this.simulatedEmailRepository = simulatedEmailRepository;
    }

    public void sendEmail(String recipientEmail, String subject, String body) {
        SimulatedEmail email = new SimulatedEmail();
        email.setRecipientEmail(recipientEmail);
        email.setSubject(subject);
        email.setBody(body);
        email.setSentAt(LocalDateTime.now());

        simulatedEmailRepository.save(email);
    }
}
