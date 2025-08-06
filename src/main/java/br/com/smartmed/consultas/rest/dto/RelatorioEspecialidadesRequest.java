package br.com.smartmed.consultas.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioEspecialidadesRequest {
    @NotNull(message = "A data de início é obrigotório!")
    LocalDate datainicio;
    @NotNull(message = "A data de fim é obrigatório!")
    LocalDate dataFim;

}
