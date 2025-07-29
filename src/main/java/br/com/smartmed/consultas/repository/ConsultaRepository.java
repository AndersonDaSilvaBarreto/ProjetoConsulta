package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.ConsultaModel;
import br.com.smartmed.consultas.rest.dto.ConsultaHistoricoDTO;
import br.com.smartmed.consultas.rest.dto.ConsultaHistoricoInputDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaModel, Long> {
    List<ConsultaModel> findByDataHoraConsulta(LocalDateTime pDataHoraConsulta);
    List<ConsultaModel> findByStatus(String pStatus);
    List<ConsultaModel> findByPacienteID(int pPacienteID);
    List<ConsultaModel> findByMedicoID(int pMedicoID);
    List<ConsultaModel> findByFormaPagamentoID(int pFormaPagamentoID);
    List<ConsultaModel> findByConvenioID(int pConvenioID);
    List<ConsultaModel> findByRecepcionistaID(int pRecepcionistaID);

    @Query(value = "SELECT " +
            "c.dataHoraConsulta," +
            "m.nome AS medico," +
            "e.nome AS especialidade, " +
            "c.valor, " +
            "c.status, " +
            "c.observacoes  " +
            "FROM consulta c " +
            "JOIN medico m ON c.medicoID = m.id " +
            "JOIN especialidade e ON m.especialidadeID = e.id  " +
            "WHERE c.pacienteID = :#{#filtro.pacienteID} " +
            "AND (:#{#filtro.dataInicio} IS NULL OR c.dataHoraConsulta >= :#{#filtro.dataInicio})  " +
            "AND (:#{#filtro.dataFim} IS NULL OR CAST(c.dataHoraConsulta AS DATE)  <= :#{#filtro.dataFim}) " +
            "AND (:#{#filtro.medicoID} IS NULL OR c.medicoID = :#{#filtro.medicoID}) " +
            "AND (:#{#filtro.status} IS NULL OR c.status = :#{#filtro.status}) " +
            "AND (:#{#filtro.especialidadeID} IS NULL OR m.especialidadeID = :#{#filtro.especialidadeID}) " +
            "ORDER BY c.dataHoraConsulta DESC ",
            nativeQuery = true )
    List<ConsultaHistoricoDTO> buscarHistoricoComFiltros(@Param("filtro") ConsultaHistoricoInputDTO filtro);

}
