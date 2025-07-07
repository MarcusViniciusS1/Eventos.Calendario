package com.eventos.calendario.jwt;


import com.eventos.calendario.model.Usuarios;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${spring.seguranca.segredo}")
    private String secret;

    @Value("${spring.seguranca.tempo_validade}")
    private Long expirationTime;

    public String gerarToken(Usuarios usuarios) throws Exception {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("project-b")
                    .withSubject(usuarios.getUsername())
                    .withExpiresAt(this.gerarExpiracaoData())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException ex) {
            ex.printStackTrace();
            throw new Exception("Erro ao gerar token!");
        }
    }

    public String validaToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("project-b")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            return null;
        }
    }

    private Instant gerarExpiracaoData() {
        return LocalDateTime.now().plusMinutes(expirationTime).toInstant(ZoneOffset.of("-03:00"));
    }
}
