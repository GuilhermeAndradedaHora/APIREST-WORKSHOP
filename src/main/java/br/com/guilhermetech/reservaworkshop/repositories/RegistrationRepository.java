package br.com.guilhermetech.reservaworkshop.repositories;

import br.com.guilhermetech.reservaworkshop.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

//Permite operações CRUD básicas
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    //Busca todas as inscrições de um usuário pelo id
    List<Registration> findAllByUserId(@Param(value = "userId") Long userId);
    //verifica se existe uma inscrição do usuário em um workshop
    boolean existsByUserIdAndWorkshop_Id(Long userId, Long workshopId);
}
