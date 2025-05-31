package br.com.smartmed.consultas.rest.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PacienteDTO {
    private int id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private String email;
}
