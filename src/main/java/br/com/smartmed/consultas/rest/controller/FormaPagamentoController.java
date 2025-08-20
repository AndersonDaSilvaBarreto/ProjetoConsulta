package br.com.smartmed.consultas.rest.controller;

import br.com.smartmed.consultas.model.FormaPagamentoModel;
import br.com.smartmed.consultas.rest.dto.FormaPagamentoDTO;
import br.com.smartmed.consultas.service.FormaPagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/formaPagamentos")
public class FormaPagamentoController {
    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoDTO> obterPorId(@PathVariable int id) {
        FormaPagamentoDTO formaPagamentoDTO = formaPagamentoService.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(formaPagamentoDTO);
    }
    @GetMapping()
    public ResponseEntity<List<FormaPagamentoDTO>> obterTodos() {
        List<FormaPagamentoDTO> formaPagamentoDTOList = formaPagamentoService.obterTodos();
        return ResponseEntity.ok(formaPagamentoDTOList);
    }
    @PostMapping()
    public ResponseEntity<FormaPagamentoDTO> salvar(@Valid @RequestBody FormaPagamentoModel novaFormaPagamento) {
        FormaPagamentoDTO novaFormaPagamentoDTO = formaPagamentoService.salvar(novaFormaPagamento);
        return  ResponseEntity.status(HttpStatus.CREATED).body(novaFormaPagamentoDTO);
    }
    @PutMapping
    public ResponseEntity<FormaPagamentoDTO> atualizar(@Valid @RequestBody FormaPagamentoModel formaPagamentoExistente) {
        FormaPagamentoDTO formaPagamentoExistenteDTO = formaPagamentoService.atualizar(formaPagamentoExistente);
        return ResponseEntity.status(HttpStatus.OK).body(formaPagamentoExistenteDTO);
    }
    @DeleteMapping
    public void deletar(@Valid @RequestBody FormaPagamentoModel formaPagamentoExistente) {
        formaPagamentoService.deletar(formaPagamentoExistente);
    }
}
