package br.com.smartmed.consultas.repository;


import br.com.smartmed.consultas.model.RecepcionistaModel;
import br.com.smartmed.consultas.rest.dto.RecepcionistaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable; // <-- ADICIONE ESTA LINHA
import java.time.LocalDate;
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

    @Query("""
        SELECT NEW br.com.smartmed.consultas.rest.dto.RecepcionistaDTO(
            r.nome,
            r.cpf,
            r.email,
            r.dataAdmissao,
            r.ativo
        )
        FROM RecepcionistaModel r
        WHERE
            (:status IS NULL OR r.ativo = :status) AND
            (:dataInicio IS NULL OR r.dataAdmissao >= :dataInicio) AND
            (:dataFim IS NULL OR r.dataAdmissao <= :dataFim)
    """)
    Page<RecepcionistaDTO> listagemDeRecepcionistasComFiltro(
            @Param("status") Boolean status,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            Pageable pageable
    );
}
