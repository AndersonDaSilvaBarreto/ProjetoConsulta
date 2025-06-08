package br.com.smartmed.consultas.repository;


import br.com.smartmed.consultas.model.RecepcionistaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface RecepcionistaRepository extends JpaRepository<RecepcionistaModel, Integer> {
    List<RecepcionistaModel> findByNomeContaining(String pNome);
    Optional<RecepcionistaModel> findByCpf(String pCpf);
    boolean existsByCpf(String pCpf);
    Optional<RecepcionistaModel> findByTelefone(String pTelefone);
    boolean existsByTelefone(String telefone);
    Optional<RecepcionistaModel> findByEmail(String pEmail);
    boolean existsByEmail(String email);
    List<RecepcionistaModel> findByAtivo(boolean pAtivo);

}
