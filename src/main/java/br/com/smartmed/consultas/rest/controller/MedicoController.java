package br.com.smartmed.consultas.rest.controller;

import br.com.smartmed.consultas.model.MedicoModel;
import br.com.smartmed.consultas.rest.dto.MedicoDTO;
import br.com.smartmed.consultas.service.MedicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medico")
public class MedicoController {
    @Autowired
    private MedicoService medicoService;

    @GetMapping("/{id}")
    public ResponseEntity<MedicoDTO> obterPorId(@PathVariable int id) {
        MedicoDTO medicoDTO = medicoService.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(medicoDTO);
    }
    @GetMapping()
    public ResponseEntity<List<MedicoDTO>> obterTodos() {
        List<MedicoDTO> medicoDTOList = medicoService.obterTodos();
        return ResponseEntity.ok(medicoDTOList);
    }
    @PostMapping()
    public ResponseEntity<MedicoDTO> salvar(@Valid @RequestBody MedicoModel novoMedico) {
        MedicoDTO novoMedicoDTO = medicoService.salvar(novoMedico);
        return  ResponseEntity.status(HttpStatus.CREATED).body(novoMedicoDTO);
    }
    @PutMapping
    public ResponseEntity<MedicoDTO> atualizar(@Valid @RequestBody MedicoModel medicoExistente) {
        MedicoDTO medicoExistenteDTO = medicoService.atualizar(medicoExistente);
        return ResponseEntity.status(HttpStatus.OK).body(medicoExistenteDTO);
    }
    @DeleteMapping
    public void deletar(@Valid @RequestBody MedicoModel medicoExistente) {
        medicoService.deletar(medicoExistente);
    }

}
