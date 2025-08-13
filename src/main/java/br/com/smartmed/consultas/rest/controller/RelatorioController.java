package br.com.smartmed.consultas.rest.controller;

import br.com.smartmed.consultas.rest.dto.*;
import br.com.smartmed.consultas.service.RelatorioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {
    @Autowired
    private RelatorioService relatoriosService;

    @GetMapping("/faturamento")
    public ResponseEntity<RelatorioFaturamentoPorPeriodoResponseDTO> gerarRelatorioFaturamentoPorPeriodo(
            @RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim
    ) {
        RelatorioFaturamentoPorPeriodoRequestDTO requestDTO = new RelatorioFaturamentoPorPeriodoRequestDTO();
        requestDTO.setDataInicio(dataInicio);
        requestDTO.setDataFim(dataFim);

        RelatorioFaturamentoPorPeriodoResponseDTO responseDTO = relatoriosService.gerarRelatorioDeFaturamento(requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/especialidades-frequentes")
    public ResponseEntity<List<RelatorioEspecialidadesResponse>> gerarRelatoriosEspecialidades(
            @Valid @RequestBody RelatorioEspecialidadesRequest request
            ) {
        List<RelatorioEspecialidadesResponse> relatorio = relatoriosService.gerarRelatorioEspecialidades(request);
        return ResponseEntity.ok(relatorio);
    }

    @PostMapping("/medicos-mais-ativos")
    public ResponseEntity<List<RankingMedicosResponse>> gerarRelatorioRankingMedicos(@Valid @RequestBody RankingMedicosRequest request) {
        List<RankingMedicosResponse> response = relatoriosService.gerarRelatorioRankingMedicos(request);
        return ResponseEntity.ok(response);
    }

}
