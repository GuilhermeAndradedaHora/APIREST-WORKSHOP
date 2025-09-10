package br.com.guilhermetech.reservaworkshop.repositories;

import br.com.guilhermetech.reservaworkshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
