package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.ConsultaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface ConsultaRepository extends JpaRepository<ConsultaModel, Integer> {
    List<ConsultaModel> findByDataHoraConsulta(LocalDateTime pDataHoraConsulta);
    List<ConsultaModel> findByStatus(String pStatus);
    List<ConsultaModel> findByPacienteID(int pPacienteID);
    List<ConsultaModel> findByMedicoID(int pMedicoID);
    List<ConsultaModel> findByFormaPagamentoID(int pFormaPagamentoID);
    List<ConsultaModel> findByConvenioID(int pConvenioID);
    List<ConsultaModel> findByRecepcionistaID(int pRecepcionistaID);

}
