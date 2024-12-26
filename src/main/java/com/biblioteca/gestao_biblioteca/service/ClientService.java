package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.dtos.request.AtualizarClienteDTO;
import com.biblioteca.gestao_biblioteca.dtos.request.ClientCreateRequest;
import com.biblioteca.gestao_biblioteca.dtos.response.ClientResponse;
import com.biblioteca.gestao_biblioteca.enums.Papel;
import com.biblioteca.gestao_biblioteca.functions.GeneratorCode;
import com.biblioteca.gestao_biblioteca.models.Auth;
import com.biblioteca.gestao_biblioteca.models.Book;
import com.biblioteca.gestao_biblioteca.models.Client;
import com.biblioteca.gestao_biblioteca.repository.AuthRepository;
import com.biblioteca.gestao_biblioteca.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    private final AuthRepository authRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientService(AuthRepository authRepository, ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Client create(Client client) {
        return clientRepository.save(client);
    }

    @Transactional
    public void registrar(ClientCreateRequest dto) {
        try {
            Client cliente;

            if (clientRepository.existsByEmail(dto.email()) || clientRepository.existsByNuit(dto.nuit())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Cliente já existe!");
            }

            String encryptedPassword = passwordEncoder.encode(dto.senha());
            Auth auth = new Auth(dto.email(), encryptedPassword, Papel.CLIENTE);

            cliente = new Client();
            cliente.setCode(GeneratorCode.generateCode());
            cliente.setName(dto.name());
            cliente.setSurname(dto.surname());
            cliente.setNuit(dto.nuit());
            cliente.setEmail(dto.email());
            cliente.setPhone(dto.phone());
            cliente.setAddress(dto.address());
            cliente.setCity(dto.city());
            cliente.setPostalCode(dto.postalCode());
            cliente.setDocumentNumber(dto.documentNumber());
            cliente.setSenha(encryptedPassword);

            authRepository.save(auth);
            create(cliente);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar cliente", e);
        }
    }

    @Transactional
    public void atualizaar(AtualizarClienteDTO dto) {
        try {
            Client cliente = clientRepository.findByNuit(dto.nuit())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

            if (!cliente.getNuit().equals(dto.nuit()) || !cliente.getEmail().equals(dto.email())) {
                if (clientRepository.existsByEmail(dto.email()) || clientRepository.existsByNuit(dto.nuit())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Cliente já existe!");
                }
            }

            cliente.setCode(dto.code());
            cliente.setName(dto.name());
            cliente.setSurname(dto.surname());
            cliente.setNuit(dto.nuit());
            cliente.setEmail(dto.email());
            cliente.setPhone(dto.phone());
            cliente.setAddress(dto.address());
            cliente.setCity(dto.city());
            cliente.setPostalCode(dto.postalCode());
            cliente.setDocumentNumber(dto.documentNumber());

            this.create(cliente);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar cliente", e);
        }
    }

    public List<ClientResponse> listar() {
        List<Client> clientes = clientRepository.findAll();
        List<ClientResponse> dtos = new ArrayList<>();

        for (Client cliente : clientes) {
            ClientResponse clientDTO = new ClientResponse(
                    cliente.getCode(),
                    cliente.getName(),
                    cliente.getSurname(),
                    cliente.getNuit(),
                    cliente.getDocumentNumber(),
                    cliente.getEmail(),
                    cliente.getPhone(),
                    cliente.getAddress(),
                    cliente.getCity(),
                    cliente.getPostalCode(),
                    cliente.getActive()
            );
            dtos.add(clientDTO);
        }
        return dtos;
    }

    public ClientResponse listarPorCode(String code){
        Client client = clientRepository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        return new ClientResponse(
                client.getCode(),
                client.getName(),
                client.getSurname(),
                client.getNuit(),
                client.getDocumentNumber(),
                client.getEmail(),
                client.getPhone(),
                client.getAddress(),
                client.getCity(),
                client.getPostalCode(),
                client.getActive()
        );
    }
}
