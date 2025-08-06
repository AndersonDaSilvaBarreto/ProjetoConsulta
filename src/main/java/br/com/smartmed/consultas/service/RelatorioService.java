package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.repository.ConsultaRepository;
import br.com.smartmed.consultas.rest.dto.RelatorioEspecialidadesRequest;
import br.com.smartmed.consultas.rest.dto.RelatorioEspecialidadesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private ConsultaRepository consultaRepository;

    public List<RelatorioEspecialidadesResponse> gerarRelatorioEspecialidades(RelatorioEspecialidadesRequest request) {
        LocalDateTime dataInicio =  request.getDatainicio().atStartOfDay();
        LocalDateTime dataFim = request.getDataFim().plusDays(1).atStartOfDay();

        return consultaRepository.findEspecialidadesMaisAtendidas(dataInicio,dataFim);

    }
}
