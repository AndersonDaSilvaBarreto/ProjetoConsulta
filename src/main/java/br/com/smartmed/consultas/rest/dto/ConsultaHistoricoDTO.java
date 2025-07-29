package br.com.smartmed.consultas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaHistoricoDTO {
    private LocalDate dataHora;
    private String medico;
    private String especialidade;
    private int valor;
    private String status;
    private String observacoes;

}
