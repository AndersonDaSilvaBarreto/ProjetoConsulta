package br.com.smartmed.consultas.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankingMedicosRequest {
    @NotNull(message = "Mês é obrigatório")
    private int mes;
    @NotNull(message = "Ano é obrigatório")
    private int ano;
}
