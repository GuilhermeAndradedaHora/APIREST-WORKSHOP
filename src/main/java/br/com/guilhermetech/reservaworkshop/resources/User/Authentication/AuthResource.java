package br.com.guilhermetech.reservaworkshop.resources.User.Authentication;

import br.com.guilhermetech.reservaworkshop.resources.User.Authentication.dtos.AuthRequest;
import br.com.guilhermetech.reservaworkshop.resources.User.Authentication.dtos.RegistRequest;
import br.com.guilhermetech.reservaworkshop.services.User.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthResource {
    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        var token = this.service.getToken(authRequest.email(), authRequest.password());
        return ResponseEntity.ok(token);
    }

    @PostMapping(value = "/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> register(@RequestBody RegistRequest registRequest) {
        this.service.registrarAdmin(registRequest.email(), registRequest.password());
        return ResponseEntity.ok().build();
    }
}