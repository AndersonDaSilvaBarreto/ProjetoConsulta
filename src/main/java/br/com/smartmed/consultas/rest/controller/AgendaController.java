package br.com.smartmed.consultas.rest.controller;

import br.com.smartmed.consultas.exception.BusinessRuleException;
import br.com.smartmed.consultas.model.MedicoModel;
import br.com.smartmed.consultas.repository.MedicoRepository;
import br.com.smartmed.consultas.rest.dto.ConsultaAgendaRequest;
import br.com.smartmed.consultas.rest.dto.ConsultaAgendaResponse;
import br.com.smartmed.consultas.service.AgendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicos/agenda")
public class AgendaController {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private AgendaService agendaService;

    @PostMapping
    public ResponseEntity<ConsultaAgendaResponse> consultarAgenda(@Valid @RequestBody ConsultaAgendaRequest request) {
        MedicoModel medico = medicoRepository.findById(request.getMedicoID()).
                orElseThrow(() ->  new BusinessRuleException("Médido não existente"));
        ConsultaAgendaResponse resposta = agendaService.consultarAgenda(medico, request.getData());
        return ResponseEntity.ok(resposta);
    }
}
