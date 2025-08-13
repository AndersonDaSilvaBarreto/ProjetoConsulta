package br.com.smartmed.consultas.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReagendamentoDeConsultaRequest {
    @NotNull(message = "O id da consulta não pode ser nulo")
    private long consultaID;
    @NotNull(message = "Não pode ser nula a nova data e hora")
    private LocalDateTime novaDataHora;
    String motivo;
}
