package br.com.guilhermetech.reservaworkshop.resources.User.Authentication;

import br.com.guilhermetech.reservaworkshop.infra.Security.JwtUtil;
import br.com.guilhermetech.reservaworkshop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthResource {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public String login(@RequestParam String email, @RequestParam String password){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        var user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        return jwtUtil.generateToken(user.getEmail(), user.getPassword());
    }
}
