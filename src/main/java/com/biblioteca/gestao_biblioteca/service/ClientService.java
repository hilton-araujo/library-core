package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.dtos.CreateClientDTO;
import com.biblioteca.gestao_biblioteca.dtos.Response.ClientDTO;
import com.biblioteca.gestao_biblioteca.enums.Papel;
import com.biblioteca.gestao_biblioteca.exceptions.ContentAlreadyExistsException;
import com.biblioteca.gestao_biblioteca.models.Auth;
import com.biblioteca.gestao_biblioteca.models.Cliente;
import com.biblioteca.gestao_biblioteca.repository.AuthRepository;
import com.biblioteca.gestao_biblioteca.repository.ClientRepository;
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

    public Cliente create(CreateClientDTO dto) throws ContentAlreadyExistsException {

        if (clientRepository.existsByEmail(dto.email())) {
            throw new ContentAlreadyExistsException("Já existe um cliente com esse email!");
        }

        Cliente cliente = new Cliente();
        cliente.setName(dto.name());
        cliente.setEmail(dto.email());
        cliente.setSenha(dto.senha());

        Cliente novoCliente = clientRepository.save(cliente);

        String encryptedPassword = passwordEncoder.encode(dto.senha());
        Auth auth = new Auth(dto.email(), encryptedPassword, Papel.CLIENTE);

        authRepository.save(auth);

        return novoCliente;
    }

    public List<ClientDTO> listar(){
        List<Cliente> clientes = clientRepository.findAll();
        List<ClientDTO> dtos = new ArrayList<>();

        for (Cliente cliente : clientes){
            ClientDTO clientDTO = new ClientDTO(
                    cliente.getId(),
                    cliente.getName(),
                    cliente.getEmail()
            );
            dtos.add(clientDTO);
        }
        return dtos;
    }
}
