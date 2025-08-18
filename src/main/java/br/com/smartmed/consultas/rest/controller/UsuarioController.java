package br.com.smartmed.consultas.rest.controller;

import br.com.smartmed.consultas.model.UsuarioModel;
import br.com.smartmed.consultas.rest.dto.AutenticacaoUsuarioRequest;
import br.com.smartmed.consultas.rest.dto.AutenticacaoUsuarioResponse;
import br.com.smartmed.consultas.rest.dto.UsuarioCadastroResponse;
import br.com.smartmed.consultas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @Transactional
    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioCadastroResponse> cadastrarUsuario(
            @Valid @RequestBody UsuarioModel request) {
            UsuarioCadastroResponse response = usuarioService.cadastrarUsuario(request);
            return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AutenticacaoUsuarioResponse> autenticacaoUsuario(
            @Valid @RequestBody AutenticacaoUsuarioRequest request) {
        AutenticacaoUsuarioResponse response = usuarioService.autenticacaoUsuario(request);
        return ResponseEntity.ok(response);
    }

}
