package br.com.smartmed.consultas.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AutenticacaoUsuarioRequest {

    @Email(message = "E-mail invalido")
    @NotNull(message = "O email não pode ser nulo!")
    @NotBlank(message = "O email não pode ser vazio!")
    private String email;

    @NotNull(message = "A senha não pode ser nula!")
    @NotBlank(message = "É obrigatório o preenchimento da senha!")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String senha;

}
