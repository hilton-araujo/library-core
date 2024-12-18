package com.biblioteca.gestao_biblioteca.controllers;


import com.biblioteca.gestao_biblioteca.dtos.AuthDTO;
import com.biblioteca.gestao_biblioteca.dtos.AuthResisterDTO;
import com.biblioteca.gestao_biblioteca.dtos.Response.ResponseApi;
import com.biblioteca.gestao_biblioteca.infra.TokenService;
import com.biblioteca.gestao_biblioteca.models.Auth;
import com.biblioteca.gestao_biblioteca.repository.AuthRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthRepository repository;

    @Autowired
    private TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity<ResponseApi> login(@RequestBody @Valid AuthDTO data){
        var usenamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usenamePassword);
        var token = tokenService.gerarToken((Auth) auth.getPrincipal());

        Map<String, String> userAuthToken = new HashMap<>();
        userAuthToken.put("token", token);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Login efectuado com sucesso", userAuthToken));
    }

    @PostMapping("/resister")
    public ResponseEntity<?> resister(@RequestBody @Valid AuthResisterDTO data){
        if (this.repository.findByUsername(data.username()) != null) return ResponseEntity.badRequest().build();
        String encriptedPassword = new BCryptPasswordEncoder().encode(data.password());

        Auth newUser = new Auth(data.username(), encriptedPassword, data.roles());

        this.repository.save(newUser);
        return ResponseEntity.ok().build();
    }
}