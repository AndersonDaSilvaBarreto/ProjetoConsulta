package br.com.smartmed.consultas.rest.controller;

import br.com.smartmed.consultas.model.MedicoModel;
import br.com.smartmed.consultas.repository.MedicoRepository;
import br.com.smartmed.consultas.rest.dto.ConsultaAgendaRequest;
import br.com.smartmed.consultas.rest.dto.ConsultaAgendaResponse;
import br.com.smartmed.consultas.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/medicos/agenda")
public class AgendaController {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private AgendaService agendaService;

    @PostMapping
    public ResponseEntity<?> consultarAgenda(@RequestBody ConsultaAgendaRequest request) {
        Optional<MedicoModel> opt = medicoRepository.findById(Math.toIntExact(request.getMedicoID()));
        if (opt.isEmpty() || !opt.get().isAtivo()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Médico inativo ou inexistente");
        }

        MedicoModel medico = opt.get();
        ConsultaAgendaResponse resposta = agendaService.consultarAgenda(medico, request.getData());
        return ResponseEntity.ok(resposta);
    }
}
