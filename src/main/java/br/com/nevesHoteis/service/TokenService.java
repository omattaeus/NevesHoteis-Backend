package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {
    public String createdToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256("Testing");
            return JWT.create()
                    .withIssuer("Neves Hoteis")
                    .withSubject(user.getLogin())
                    .withExpiresAt(getDataExpires())
                    .sign(algorithm);

        } catch (JWTCreationException exception){
            exception.printStackTrace();
            throw new JWTVerificationException("");
        }
    }

    private Instant getDataExpires() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256("Testing");
           return JWT.require(algorithm)
                    .withIssuer("Neves Hoteis")
                    .build()
                   .verify(token)
                   .getSubject();
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Token nulo, expirado ou inválido");
        }
    }
}
