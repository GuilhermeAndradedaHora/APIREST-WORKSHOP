package br.com.guilhermetech.reservaworkshop.repositories;

import br.com.guilhermetech.reservaworkshop.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findAllByUserId(@Param(value = "userId") Long userId);
    boolean existsByUserIdAndWorkshop_Id(Long userId, Long workshopId);
}
