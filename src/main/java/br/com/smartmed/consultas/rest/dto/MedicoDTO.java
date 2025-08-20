package br.com.smartmed.consultas.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicoDTO {
    private int id;
    private String nome;
    private String crm;
    private String telefone;
    private String email;
    private BigDecimal valorConsultaReferencia;
    private boolean ativo;
    private int especialidadeID;

}
