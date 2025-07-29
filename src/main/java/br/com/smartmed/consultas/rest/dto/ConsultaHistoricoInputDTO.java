package br.com.smartmed.consultas.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaHistoricoInputDTO {
    @NotNull(message = "O paciente não pode ser nulo!")
    private int pacienteID;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Integer medicoID;
    private String status;
    private Integer especialidadeID;
}
