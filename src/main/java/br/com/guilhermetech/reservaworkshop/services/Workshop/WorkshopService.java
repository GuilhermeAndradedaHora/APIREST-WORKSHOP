package br.com.guilhermetech.reservaworkshop.services.Workshop;

import br.com.guilhermetech.reservaworkshop.entities.Workshop;
import br.com.guilhermetech.reservaworkshop.repositories.WorkshopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkshopService {

    private final WorkshopRepository workshopRepository;

    public List<Workshop> findAll() {
        return workshopRepository.findAll();
    }

    public Workshop findById(Long id) {
        return workshopRepository.findById(id).orElseThrow(() -> new RuntimeException("Workshop not found"));
    }

    public Workshop insert(Workshop obj) {
        return workshopRepository.save(obj);
    }
}
