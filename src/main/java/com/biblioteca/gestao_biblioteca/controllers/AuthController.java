package com.biblioteca.gestao_biblioteca.controllers;


import com.biblioteca.gestao_biblioteca.dtos.request.AuthDTO;
import com.biblioteca.gestao_biblioteca.dtos.request.AuthResisterDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.ResponseApi;
import com.biblioteca.gestao_biblioteca.infra.TokenService;
import com.biblioteca.gestao_biblioteca.infrastructure.exceptions.NotFoundException;
import com.biblioteca.gestao_biblioteca.models.Auth;
import com.biblioteca.gestao_biblioteca.repository.AuthRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final AuthRepository repository;

    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, AuthRepository repository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.tokenService = tokenService;
    }


    @PostMapping("/login")
    @Operation(summary = "Realiza o login no sistema")
    public ResponseEntity<ResponseApi> login(@RequestBody @Valid AuthDTO data) throws NotFoundException {
        try {
            var usenamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
            var auth = this.authenticationManager.authenticate(usenamePassword);
            var token = tokenService.gerarToken((Auth) auth.getPrincipal());

            Map<String, String> userAuthToken = new HashMap<>();
            userAuthToken.put("token", token);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Login efectuado com sucesso", userAuthToken));
        }catch (ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Ocorreu um erro ao realizar login", null));
        }
    }

    @PostMapping("/resister")
    @Operation(summary = "Realiza o cadastro de usuários no sistema")
    public ResponseEntity<ResponseApi> resister(@RequestBody @Valid AuthResisterDTO data) {
        if (this.repository.findByUsername(data.username()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseApi("Usuário já existe", null));
        }

        String encriptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Auth newUser = new Auth(data.username(), encriptedPassword, data.roles());
        this.repository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseApi("Usuário cadastrado com sucesso", null));
    }

}