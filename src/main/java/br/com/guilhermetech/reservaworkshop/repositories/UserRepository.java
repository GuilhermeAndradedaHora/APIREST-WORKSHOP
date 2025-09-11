package br.com.guilhermetech.reservaworkshop.repositories;

import br.com.guilhermetech.reservaworkshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    //Optional para evitar null
    Optional<User> findByEmail(String email);
}
