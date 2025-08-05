package br.com.smartmed.consultas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecepcionistaDTO {
    private int id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private LocalDate dataAdmissao;
    private LocalDate dataDemissao;
    private String telefone;
    private String email;
    private boolean ativo;

    public RecepcionistaDTO(String nome, String cpf, String email, LocalDate dataAdmissao, boolean ativo) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.dataAdmissao = dataAdmissao;
        this.ativo = ativo;
    }
}
