package br.com.guilhermetech.reservaworkshop.resources.registration;

import br.com.guilhermetech.reservaworkshop.entities.Registration;
import br.com.guilhermetech.reservaworkshop.resources.registration.dtos.RegistrationRequest;
import br.com.guilhermetech.reservaworkshop.services.Registration.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/registration")
@RequiredArgsConstructor
public class RegistrationResource {
    private final RegistrationService service;


    @GetMapping
    @PreAuthorize(value = "validatorSecurity.canExecute('USER', 'ADMIN')")
    public ResponseEntity<List<Registration>> getItems(@RequestParam(value = "userId") Long userId) {
        var items = this.service.findByUserId(userId);
        return ResponseEntity.ok(items);
    }

    @PostMapping
    public ResponseEntity<Registration> postItem(@RequestBody RegistrationRequest itemRequest, UriComponentsBuilder builder) {
        var item = this.service.insert(itemRequest.convertToEntity(), itemRequest.workshopId());
        var uri = builder.path("/registration/{id}").buildAndExpand(item.getId()).toUri();
        return ResponseEntity.created(uri).body(item);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteItem(@PathVariable(value = "id") Long id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
