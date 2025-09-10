package br.com.guilhermetech.reservaworkshop.resources.Worshop;

import br.com.guilhermetech.reservaworkshop.entities.Workshop;
import br.com.guilhermetech.reservaworkshop.services.Workshop.WorkshopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/workshop")
@RequiredArgsConstructor
public class WorkshopResource {
    private final WorkshopService service;

    @GetMapping
    public ResponseEntity<List<Workshop>> findAll() {
        List<Workshop> obj = service.findAll();
        return ok(obj);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        return ok(this.service.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody WorkshopRequest itemRequest, UriComponentsBuilder builder) {
        var item = this.service.insert(itemRequest.convertToEntity());
        var uri = builder.path("/workshop/{id}").buildAndExpand(item.getId()).toUri();
        return ResponseEntity.created(uri).body(item);
    }
}
