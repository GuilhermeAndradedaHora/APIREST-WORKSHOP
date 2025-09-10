package br.com.guilhermetech.reservaworkshop.repositories;

import br.com.guilhermetech.reservaworkshop.entities.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkshopRepository extends JpaRepository<Workshop,Long> {
}
