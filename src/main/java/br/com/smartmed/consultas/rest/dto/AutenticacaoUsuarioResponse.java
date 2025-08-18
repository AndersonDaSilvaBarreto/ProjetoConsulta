package br.com.smartmed.consultas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AutenticacaoUsuarioResponse {
    private String mensagem;
    private UsuarioDTO usuario;
    private String token;
}
