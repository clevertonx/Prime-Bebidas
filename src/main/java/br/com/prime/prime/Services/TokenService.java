package br.com.prime.prime.Services;

import br.com.prime.prime.models.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secreta;

    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secreta);
            return JWT.create()
                    .withIssuer("Estabelecimentos")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(LocalDateTime.now()
                            .plusMinutes(1)
                            .toInstant(ZoneOffset.of("-03:00"))
                    ).sign(Algorithm.HMAC256(String.valueOf(secreta)));
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar Token JWT: ", exception);
        }

    }

    public String getSubject(String tokenJWT) {

        return JWT.require(Algorithm.HMAC256(secreta))
                .withIssuer("estabelecimentos")
                .build().verify(tokenJWT).getSubject();
    }
}
