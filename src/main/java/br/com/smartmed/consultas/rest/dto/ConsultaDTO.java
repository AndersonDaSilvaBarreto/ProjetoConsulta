package br.com.smartmed.consultas.rest.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultaDTO {
    private long id;
    private LocalDateTime dataHoraConsulta;
    private String status;
    private float valor;
    private String observacoes;
    private int pacienteID;
    private int medicoID;
    private Integer formaPagamentoID;
    private Integer convenioID;
    private Integer recepcionistaID;
}
