package br.com.smartmed.consultas.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastrarConsultaRecepcionistaRequest {
    @NotNull(message = "Data e hora não pode ser nulo!")
    private LocalDateTime dataHora;
    @NotNull(message = "A duração em minutos não pode ser nulo!")
    private int duracaoMinutos;
    @NotNull(message = "O id do paciente não pode ser nulo!")
    private int pacienteID;
    @NotNull(message = "O id do medico não pode ser nulo!")
    private int medicoID;
    private Integer convenioID;
    private Integer formaPagamentoID;
    @NotNull(message = "O id de recepcionista não pode ser nulo!")
    private Integer recepcionistaID;
}
