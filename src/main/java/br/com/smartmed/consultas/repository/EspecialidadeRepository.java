package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.EspecialidadeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EspecialidadeRepository extends JpaRepository<EspecialidadeModel, Integer> {
    List<EspecialidadeModel> findByNomeContaining(String pNome);
}
