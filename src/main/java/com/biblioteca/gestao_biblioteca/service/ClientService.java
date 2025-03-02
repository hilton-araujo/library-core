package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.dtos.request.AtualizarClienteDTO;
import com.biblioteca.gestao_biblioteca.dtos.request.ClientCreateRequest;
import com.biblioteca.gestao_biblioteca.dtos.response.*;
import com.biblioteca.gestao_biblioteca.enums.Role;
import com.biblioteca.gestao_biblioteca.functions.GeneratorCode;
import com.biblioteca.gestao_biblioteca.infrastructure.exceptions.ContentAlreadyExistsException;
import com.biblioteca.gestao_biblioteca.infrastructure.exceptions.NotFoundException;
import com.biblioteca.gestao_biblioteca.models.Auth;
import com.biblioteca.gestao_biblioteca.models.Client;
import com.biblioteca.gestao_biblioteca.repository.AuthRepository;
import com.biblioteca.gestao_biblioteca.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    private void verificarConflitos(String email, String nuit) throws ContentAlreadyExistsException {
        if (clientRepository.existsByEmail(email) || clientRepository.existsByNuit(nuit)) {
            throw new ContentAlreadyExistsException("Cliente já existe!");
        }
    }

    private Client buscarClientePorNuit(String nuit) throws NotFoundException {
        return clientRepository.findByNuit(nuit)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
    }

    private Client buscarClientePorCode(String code) throws NotFoundException{
        return clientRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
    }


    @Transactional
    public void registrar(ClientCreateRequest dto) {
        try {
            Client cliente;

            verificarConflitos(dto.email(), dto.nuit());

            String encryptedPassword = passwordEncoder.encode(dto.senha());
            Auth auth = new Auth(dto.email(), encryptedPassword, Role.CLIENTE);

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
            this.create(cliente);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar cliente", e);
        }
    }

    @Transactional
    public void atualizaar(AtualizarClienteDTO dto) {
        try {
            Client cliente = buscarClientePorNuit(dto.nuit());

            if (!cliente.getNuit().equals(dto.nuit()) || !cliente.getEmail().equals(dto.email())) {
                verificarConflitos(dto.email(), dto.nuit());
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
        return clientes.stream()
                .map(cliente -> new ClientResponse(
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
                ))
                .toList();
    }

    public ClientResponseDetails listarDetalhesCliente(String nuit) throws NotFoundException {
        Client client = clientRepository.findByNuit(nuit)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));


        List<OrderResponseDTO> orderResponseDTOS = client.getOrders().stream()
                .map(order -> new OrderResponseDTO(
                        order.getId(),
                        order.getCode(),
                        order.getDesignation(),
                        order.getDescription(),
                        order.getClientId().getName(),
                        order.getOrderType().getDesignation()
                ))
                .toList();

        return new ClientResponseDetails(
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
                client.getActive(),
                orderResponseDTOS
        );
    }


    public void inativeClient(String code){
        try {
            if (code == null || code.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código do cliente não fornecido");
            }

            Client client = buscarClientePorCode(code);

            if (!client.getActive()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Cliente já foi inativado");
            }

            client.setActive(false);
            this.create(client);

        }catch (ResponseStatusException e){
            throw e;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro ao inativar cliente");
        }
    }

    public void ativarClient(String code){
        try {
            if (code == null || code.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código do cliente não fornecido");
            }

            Client client = buscarClientePorCode(code);

            if (client.getActive()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Cliente já foi ativado");
            }

            client.setActive(true);
            this.create(client);

        }catch (ResponseStatusException e){
            throw e;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro ao inativar cliente");
        }
    }
}
