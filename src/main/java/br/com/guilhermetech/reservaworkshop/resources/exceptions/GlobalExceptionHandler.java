package br.com.guilhermetech.reservaworkshop.resources.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Map;

//Torna a classe um capturador global de exceções
@ControllerAdvice
public class GlobalExceptionHandler {

    //Requisição sem token http 401
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthentication(AuthenticationException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "User unauthorized");
    }

    //Usário de role não exigida http 403
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        return buildResponse(HttpStatus.FORBIDDEN, "Access denied");
    }

    //http 401
    @ExceptionHandler({JwtException.class, ExpiredJwtException.class})
    public ResponseEntity<Map<String, Object>> handleJwt(JwtException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Token invalid or expired");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Credentials are invalid");
    }

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of(
                "timestamp", Instant.now().toString(),
                "status", status.value(),
                "error", message
        ));
    }
}

