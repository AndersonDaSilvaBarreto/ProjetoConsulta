package br.com.smartmed.consultas.rest.controller;

import br.com.smartmed.consultas.rest.dto.RelatorioEspecialidadesRequest;
import br.com.smartmed.consultas.rest.dto.RelatorioEspecialidadesResponse;
import br.com.smartmed.consultas.service.RelatorioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {
    @Autowired
    private RelatorioService relatoriosService;

    @PostMapping("/especialidades-frequentes")
    public ResponseEntity<List<RelatorioEspecialidadesResponse>> gerarRelatoriosEspecialidades(
            @Valid @RequestBody RelatorioEspecialidadesRequest request
            ) {
        List<RelatorioEspecialidadesResponse> relatorio = relatoriosService.gerarRelatorioEspecialidades(request);
        return ResponseEntity.ok(relatorio);
    }


}
