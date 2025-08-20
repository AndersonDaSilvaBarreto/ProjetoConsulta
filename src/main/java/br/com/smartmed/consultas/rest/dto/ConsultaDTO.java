package br.com.smartmed.consultas.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConsultaDTO {
    private Long id;
    @NotNull(message = "A data e hora é obrigatório!")
    private LocalDateTime dataHoraConsulta;
    @NotBlank(message = "O satus é obrigatório!")
    private String status;
    @NotNull(message = "Valor é obrigatório!")
    private BigDecimal valor;
    private String observacoes;
    @NotNull(message = "O id de paciente é obrigatório!")
    private Integer pacienteID;
    @NotNull(message = "O id medico é obrigatório!")
    private Integer medicoID;

    private Integer formaPagamentoID;
    private Integer convenioID;
    @NotNull(message = "O recepcionista id é obrigatório!")
    private Integer recepcionistaID;
}
