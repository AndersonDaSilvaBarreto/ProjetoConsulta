package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.ConvenioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConvenioRepository extends JpaRepository<ConvenioModel, Integer> {
    List<ConvenioModel> findByNomeContaining(String pNome);
    Optional<ConvenioModel> findByCnpj(String pCnpj);
    boolean existsByCnpj(String cnpj);
    Optional<ConvenioModel> findByTelefone(String pTelefone);
    boolean existsByTelefone(String telefone);
    Optional<ConvenioModel> findByEmail(String pEmail);
    boolean existsByEmail(String email);
    List<ConvenioModel> findByAtivo(boolean pAtivo);
}
