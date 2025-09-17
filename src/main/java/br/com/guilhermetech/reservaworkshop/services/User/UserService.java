package br.com.guilhermetech.reservaworkshop.services.User;

import br.com.guilhermetech.reservaworkshop.entities.User;
import br.com.guilhermetech.reservaworkshop.entities.enums.UserType;
import br.com.guilhermetech.reservaworkshop.repositories.UserRepository;
import br.com.guilhermetech.reservaworkshop.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not exist" + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("email not exist"));
    }

    @Transactional
    public User insert(User user) {
        user.setPassword(this.passwordEncoder.encode("13245678"));
        user.setUserType(UserType.PART);
        var savedUser = userRepository.save(user);
        emailService.sendEmail(user.getEmail(),
                "New user registered",
                "You are receiving a registration email");
        return savedUser;
    }

    public User update(Long id, User user) {
        User entity = this.findById(id);
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals(entity.getEmail())) {
            throw new RuntimeException("Unauthorized access!");
        }
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        return userRepository.save(entity);
    }

    public void delete(Long id) {
        User entity = this.findById(id);
        userRepository.delete(entity);
    }

   public User changePassword(Long id, String newPassword) {
        User entity = this.findById(id);
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals(entity.getEmail())) {
            throw new RuntimeException("Unauthorized access!");
        }

        entity.setPassword(passwordEncoder.encode(newPassword));
        entity.setFirstLogin(false);
        return userRepository.save(entity);
    }


}
