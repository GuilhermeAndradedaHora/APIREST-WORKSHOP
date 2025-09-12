package br.com.guilhermetech.reservaworkshop.resources.User;

import br.com.guilhermetech.reservaworkshop.entities.User;
import br.com.guilhermetech.reservaworkshop.repositories.UserRepository;
import br.com.guilhermetech.reservaworkshop.resources.User.dtos.ChangePasswordRequest;
import br.com.guilhermetech.reservaworkshop.resources.User.dtos.UserRequest;
import br.com.guilhermetech.reservaworkshop.services.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>>  findAll() {
        List<User> users = userService.findAll();
        return ok().body(users);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        return ok(this.userService.findById(id));
    }

    @GetMapping(value = "/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable(value = "email") String email) {
        return ok(this.userService.findByEmail(email));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody UserRequest userRequest) {
        User item = this.userService.insert(userRequest.convertToEntity());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(item.getId()).toUri();
        return ResponseEntity.created(uri).body(item);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        this.userService.update(id, userRequest.convertToEntity());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@RequestParam(value = "id") Long id) {
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/change-password")
    public ResponseEntity<?> ChangePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest changePasswordRequest) {
        this.userService.changePassword(id, changePasswordRequest.newPassword());
        return ResponseEntity.noContent().build();
    }

}
