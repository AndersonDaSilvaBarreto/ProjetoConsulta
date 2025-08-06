package br.com.smartmed.consultas.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConsultaCancelamentoRequest {
    @NotNull(message = "ConsultaID é obrigatório!")
    long consultaID;
    @NotNull(message = "O motivo é obrigatório!")
    @NotBlank(message = "O motivo precisa ser preenchido!")
    String motivo;

}
