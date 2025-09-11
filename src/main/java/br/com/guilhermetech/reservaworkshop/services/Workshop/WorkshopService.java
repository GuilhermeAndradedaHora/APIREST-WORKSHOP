package br.com.guilhermetech.reservaworkshop.services.Workshop;

import br.com.guilhermetech.reservaworkshop.entities.Workshop;
import br.com.guilhermetech.reservaworkshop.repositories.WorkshopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    public Workshop insert(Workshop obj) {
        return workshopRepository.save(obj);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Workshop update(Long id, Workshop obj) {
        var workshop = workshopRepository.findById(id).orElseThrow(() -> new RuntimeException("Workshop not found"));
        workshop.setTitle(obj.getTitle());
        workshop.setDescription(obj.getDescription());
        workshop.setMoment(obj.getMoment());
        workshop.setLocale(obj.getLocale());
        workshop.setMaxCapacity(obj.getMaxCapacity());
        return workshopRepository.save(workshop);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Workshop obj) {
        var entity = workshopRepository.findById(obj.getId()).orElseThrow(() -> new RuntimeException("Workshop not found"));
        workshopRepository.delete(entity);
    }

}
