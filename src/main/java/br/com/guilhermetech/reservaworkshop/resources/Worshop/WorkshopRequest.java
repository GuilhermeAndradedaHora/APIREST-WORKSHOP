package br.com.guilhermetech.reservaworkshop.resources.Worshop;

import br.com.guilhermetech.reservaworkshop.entities.Workshop;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public record WorkshopRequest(String title, String description, LocalDateTime moment, String locale, Integer maxCapacity) {
    public Workshop convertToEntity() {
        return new Workshop(title, description, moment.toInstant(ZoneOffset.UTC), locale, maxCapacity);
    }
}
