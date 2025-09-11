package br.com.guilhermetech.reservaworkshop.services.User;

import br.com.guilhermetech.reservaworkshop.config.security.JwtUtil;
import br.com.guilhermetech.reservaworkshop.entities.User;
import br.com.guilhermetech.reservaworkshop.entities.enums.UserType;
import br.com.guilhermetech.reservaworkshop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public String getToken(String username, String password) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        var user = repository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        return this.jwtUtil.generateToken(user.getEmail(), user.getUserType().name());
    }

    public void registrarAdmin(String email, String password) {
        if (this.repository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        var admin = new User("Administrador", email);
        admin.setPassword(bcryptPasswordEncoder.encode(password));
        admin.setUserType(UserType.ADMIN);

        this.repository.save(admin);
    }
}
