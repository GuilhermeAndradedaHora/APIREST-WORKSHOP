package br.com.guilhermetech.reservaworkshop.resources.User;

import br.com.guilhermetech.reservaworkshop.entities.User;
import br.com.guilhermetech.reservaworkshop.resources.User.dtos.UserRequest;
import br.com.guilhermetech.reservaworkshop.services.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<User>>  findAll() {
        List<User> users = userService.findAll();
        return ok().body(users);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        return ok(this.userService.findById(id));
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
    public ResponseEntity<?> delete(@RequestParam(value = "id") Long id) {
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
