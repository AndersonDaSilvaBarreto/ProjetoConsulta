package br.com.smartmed.consultas.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConsultaAgendaRequest {
    @NotNull(message = "O id do médico não pode ser nulo")
    private Integer medicoID;
    @NotNull(message = "A data não pode ser nula!")
    private LocalDate data;

}
