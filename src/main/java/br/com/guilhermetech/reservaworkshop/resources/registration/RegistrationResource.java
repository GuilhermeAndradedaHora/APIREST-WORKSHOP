package br.com.guilhermetech.reservaworkshop.resources.registration;

import br.com.guilhermetech.reservaworkshop.entities.Registration;
import br.com.guilhermetech.reservaworkshop.services.Registration.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/registration")
@RequiredArgsConstructor
public class RegistrationResource {
    private final RegistrationService service;


    @GetMapping
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
}
