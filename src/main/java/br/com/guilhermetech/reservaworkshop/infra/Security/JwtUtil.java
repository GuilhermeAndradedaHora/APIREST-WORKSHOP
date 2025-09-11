package br.com.guilhermetech.reservaworkshop.infra.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "MYJWTSECRET_MYJWTSECRET_MYJWTSECRET_123";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    private final long EXPIRATION = 3600000; //1h

    public String generateToken(String email, String type) {
        return Jwts.builder()
                .setSubject(email)
                .claim("tipo", type)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validatorToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmail(String token) {
        return validatorToken(token).getSubject();
    }

    public String getTipo(String token) {
        return validatorToken(token).get("tipo", String.class);
    }
}
