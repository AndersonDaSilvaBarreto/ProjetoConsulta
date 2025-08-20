package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.AgendaModel;
import br.com.smartmed.consultas.model.MedicoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AgendaRepository extends JpaRepository<AgendaModel, Long> {
    Optional<AgendaModel> findByMedicoAndData(MedicoModel pMedico, LocalDate pData);
}
