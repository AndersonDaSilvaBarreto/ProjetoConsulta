package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.BlocoHorarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlocoHorarioRepository extends JpaRepository<BlocoHorarioModel, Long> {
}
