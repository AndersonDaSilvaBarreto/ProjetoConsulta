package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.repository.ConsultaRepository;
import br.com.smartmed.consultas.rest.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private ConsultaRepository consultaRepository;

    public RelatorioFaturamentoPorPeriodoResponseDTO gerarRelatorioDeFaturamento(RelatorioFaturamentoPorPeriodoRequestDTO requestDTO) {

        LocalDate dataInicioDate = requestDTO.getDataInicio();
        LocalDate dataFimDate = requestDTO.getDataFim();

        if(dataInicioDate.isAfter(dataFimDate)) {
            throw new IllegalArgumentException("A data de início não pode ser posterior à data de fim.");
        }
        LocalDateTime dataInicio = dataInicioDate.atStartOfDay();
        LocalDateTime dataFim = dataFimDate.atTime(LocalTime.MAX);

        BigDecimal totalGeral = consultaRepository.faturamentoTotal(dataInicio,dataFim);
        if (totalGeral == null) {
            totalGeral = BigDecimal.ZERO;
        }

        List<FormaPagamentoFaturamentoDTO> porFormaPagamento = consultaRepository.faturamentoPorFormaPagamento(dataInicio,dataFim);
        List<ConvenioFaturamentoDTO> porConvenio = consultaRepository.faturamentoPorConvenio(dataInicio,dataFim);
        return new RelatorioFaturamentoPorPeriodoResponseDTO(totalGeral, porFormaPagamento,porConvenio);
    }

    public List<RelatorioEspecialidadesResponse> gerarRelatorioEspecialidades(RelatorioEspecialidadesRequest request) {
        LocalDateTime dataInicio =  request.getDatainicio().atStartOfDay();
        LocalDateTime dataFim = request.getDataFim().atTime(LocalTime.MAX);

        return consultaRepository.findEspecialidadesMaisAtendidas(dataInicio,dataFim);

    }
}
