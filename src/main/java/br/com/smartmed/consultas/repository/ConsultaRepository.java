package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.*;
import br.com.smartmed.consultas.rest.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;



@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaModel, Long> {
    List<ConsultaModel> findByDataHoraConsulta(LocalDateTime pDataHoraConsulta);
    List<ConsultaModel> findByStatus(String pStatus);
    List<ConsultaModel> findByPaciente(PacienteModel pPaciente);
    List<ConsultaModel> findByMedico(MedicoModel pMedico);
    List<ConsultaModel> findByFormaPagamento(FormaPagamentoModel pFormaPagamentoID);
    List<ConsultaModel> findByConvenio(ConvenioModel pConvenio);
    List<ConsultaModel> findByRecepcionista(RecepcionistaModel pRecepcionista);

    @Query(value = "SELECT " +
            "c.dataHoraConsulta ," +
            "m.nome ," +
            "e.nome , " +
            "c.valor, " +
            "c.status, " +
            "c.observacoes  " +
            "FROM consulta c " +
            "JOIN paciente p ON c.pacienteID = p.id " +
            "JOIN medico m ON c.medicoID = m.id " +
            "JOIN especialidade e ON m.especialidadeID = e.id " +
            "WHERE c.pacienteID = :#{#filtro.pacienteID} " +
            "AND p.status = 'ativo'  " +
            "AND (:#{#filtro.dataInicio} IS NULL OR c.dataHoraConsulta >= :#{#filtro.dataInicio})  " +
            "AND (:#{#filtro.dataFim} IS NULL OR CAST(c.dataHoraConsulta AS DATE)  <= :#{#filtro.dataFim}) " +
            "AND (:#{#filtro.medicoID} IS NULL OR c.medicoID = :#{#filtro.medicoID}) " +
            "AND (:#{#filtro.status} IS NULL OR c.status = :#{#filtro.status}) " +
            "AND (:#{#filtro.especialidadeID} IS NULL OR m.especialidadeID = :#{#filtro.especialidadeID}) " +
            "ORDER BY c.dataHoraConsulta DESC ",
            nativeQuery = true )
    List<ConsultaHistoricoDTO> buscarHistoricoComFiltros(@Param("filtro") ConsultaHistoricoInputDTO filtro);

    @Modifying
    @Query(value = """
            UPDATE ConsultaModel c
            SET c.status = 'CANCELADA', c.observacoes = :motivo
            WHERE c.id = :consultaID
            """)
    void cancelamentoConsulta(@Param("consultaID") Long consultaId, @Param("motivo") String motivo);

    @Query(value = """
            SELECT NEW br.com.smartmed.consultas.rest.dto.RelatorioEspecialidadesResponse(
            e.nome,
            COUNT(c)
            )
            FROM ConsultaModel c
            JOIN c.medico m
            JOIN m.especialidade e
            WHERE
                c.status = 'REALIZADA' AND
                c.dataHoraConsulta >= :dataInicio AND
                c.dataHoraConsulta < :dataFim
                GROUP BY
                    e.nome
                ORDER BY
                    COUNT(c) DESC
            """)
    List<RelatorioEspecialidadesResponse> findEspecialidadesMaisAtendidas(
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim
    );

    @Query(value = """
            SELECT NEW br.com.smartmed.consultas.rest.dto.FormaPagamentoFaturamentoDTO(
            f.descricao,
            SUM(c.valor)
            )
            FROM ConsultaModel c
            JOIN c.formaPagamento f
            WHERE c.status = 'REALIZADA'
            AND c.formaPagamento IS NOT NULL
            AND c.dataHoraConsulta BETWEEN :dataInicio AND :dataFim
            GROUP BY f.descricao
            """)
    List<FormaPagamentoFaturamentoDTO> faturamentoPorFormaPagamento(
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim
    );

    @Query("""
            SELECT NEW br.com.smartmed.consultas.rest.dto.ConvenioFaturamentoDTO(
            co.nome,
            SUM(c.valor)
            )
            FROM ConsultaModel c
            JOIN c.convenio co
            WHERE c.status = 'REALIZADA'
            AND c.convenio IS NOT NULL
            AND c.dataHoraConsulta BETWEEN :dataInicio AND :dataFim
            GROUP BY co.nome
            """)
    List<ConvenioFaturamentoDTO> faturamentoPorConvenio(
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim
    );
    @Query("""
            SELECT SUM(c.valor)
            FROM ConsultaModel c
            WHERE c.status = 'REALIZADA' AND c.dataHoraConsulta BETWEEN :dataInicio AND :dataFim
            """)
    BigDecimal faturamentoTotal(
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim
    );
    @Query("SELECT NEW br.com.smartmed.consultas.rest.dto.RankingMedicosResponse(m.nome, COUNT(c)) " +
            "FROM ConsultaModel c " +
            "JOIN c.medico m " +
            "WHERE c.status = 'REALIZADA' " +
            "AND MONTH(c.dataHoraConsulta) = :mes " +
            "AND YEAR(c.dataHoraConsulta) = :ano " +
            "GROUP BY m.nome " +
            "ORDER BY COUNT(c) DESC")
    List<RankingMedicosResponse> findMedicosMaisAtivos(
            @Param("mes") int mes,
            @Param("ano") int ano
    );
}
