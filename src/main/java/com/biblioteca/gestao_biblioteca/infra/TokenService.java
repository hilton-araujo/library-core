package com.biblioteca.gestao_biblioteca.infra;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.biblioteca.gestao_biblioteca.models.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private static final String ISSUER = "biblioteca_api";
    private static final int EXPIRATION_HOURS = 2;

    public String gerarToken(Auth usuario) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(calcularDataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new TokenCreationException("Erro ao gerar o token para o usuário: " + usuario.getUsername(), exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new TokenVerificationException("Token inválido ou expirado.", exception);
        }
    }

    private Instant calcularDataExpiracao() {
        return LocalDateTime.now().plusHours(EXPIRATION_HOURS).toInstant(ZoneOffset.UTC);
    }

    public static class TokenCreationException extends RuntimeException {
        public TokenCreationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class TokenVerificationException extends RuntimeException {
        public TokenVerificationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
