package br.com.guilhermetech.reservaworkshop.resources.registration.dtos;

import br.com.guilhermetech.reservaworkshop.entities.Registration;

public record RegistrationRequest(Long userId, Long workshopId) {
    public Registration convertToEntity() {
        return new Registration(userId);
    }
}
