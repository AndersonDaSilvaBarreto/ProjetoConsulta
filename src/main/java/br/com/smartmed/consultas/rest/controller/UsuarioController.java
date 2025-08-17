package br.com.smartmed.consultas.rest.controller;

import br.com.smartmed.consultas.model.UsuarioModel;
import br.com.smartmed.consultas.rest.dto.UsuarioCadastroResponse;
import br.com.smartmed.consultas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    public ResponseEntity<UsuarioCadastroResponse> cadastrarUsuario(
            @Valid @RequestBody UsuarioModel request) {
            UsuarioCadastroResponse response = usuarioService.cadastrarUsuario(request);
            return ResponseEntity.ok(response);
    }
}
