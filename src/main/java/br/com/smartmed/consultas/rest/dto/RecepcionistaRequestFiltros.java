package br.com.smartmed.consultas.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecepcionistaRequestFiltros {
    @NotNull
    private String status;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    @NotNull
    private int pagina;

    @NotNull
    private int tamanhoPagina;

    private String ordenarPor;

    private String direcao;

}
