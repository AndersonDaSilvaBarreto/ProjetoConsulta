package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.EspecialidadeModel;
import br.com.smartmed.consultas.model.MedicoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface MedicoRepository extends JpaRepository<MedicoModel, Integer> {
    List<MedicoModel> findByNomeContaining(String pNome);
    Optional<MedicoModel> findByCrm(String pCrm);
    boolean existsByCrm(String pCrm);
    Optional<MedicoModel> findByTelefone(String pTelefone);
    boolean existsByTelefone(String telefone);
    Optional<MedicoModel> findByEmail(String pEmail);
    boolean existsByEmail(String email);
    List<MedicoModel> findByAtivo(boolean pAtivo);
    List<MedicoModel> findByEspecialidade(EspecialidadeModel pEspecialidade);

}
