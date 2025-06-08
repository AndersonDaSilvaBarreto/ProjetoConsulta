package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.PacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository

public interface PacienteRepository extends JpaRepository<PacienteModel, Integer> {
    List<PacienteModel> findByNomeContaining(String pNome);
    Optional<PacienteModel> findByCpf(String pCpf);
    boolean existsByCpf(String pCpf);
    Optional<PacienteModel> findByTelefone(String pTelefone);
    boolean existsByTelefone(String telefone);
    Optional<PacienteModel> findByEmail(String pEmail);
    boolean existsByEmail(String email);



}
