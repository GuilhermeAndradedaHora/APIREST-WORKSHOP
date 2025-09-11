package br.com.guilhermetech.reservaworkshop.services.Registration;

import br.com.guilhermetech.reservaworkshop.entities.Registration;
import br.com.guilhermetech.reservaworkshop.repositories.RegistrationRepository;
import br.com.guilhermetech.reservaworkshop.repositories.WorkshopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final WorkshopRepository workshopRepository;

    public List<Registration> findByUserId(Long userId){
        return this.registrationRepository.findAllByUserId(userId);
    }

    @Transactional
    public Registration insert(Registration registration, Long workshopId) {
        var workshop = this.workshopRepository.findById(workshopId).orElseThrow(() -> new RuntimeException("Workshop not found!"));
        var userId = registration.getUserId();
        //Verifica se usuário já esta inscrito
        if (this.registrationRepository.existsByUserIdAndWorkshop_Id(userId, workshopId)) {
            throw new RuntimeException("User already eregistered!");
        }
        //Verifica se Whorkshop já esta cheio
        if (workshop.getRegistrations().size() >= workshop.getMaxCapacity()) {
            throw new RuntimeException("Workshop is full!");
        }
        workshop.addRegistration(registration);
        var workshopSaved = this.workshopRepository.save(workshop);
        return workshopSaved.getRegistrations().get(workshop.getRegistrations().size() - 1);
    }
}
