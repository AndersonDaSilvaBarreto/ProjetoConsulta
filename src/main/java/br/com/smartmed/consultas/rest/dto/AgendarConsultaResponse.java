package br.com.smartmed.consultas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendarConsultaResponse {
    private long consultaID;
    private LocalDateTime dataHoraConsulta;
    private String medico;
    private String paciente;
    private BigDecimal valorConsulta;
    private String status;
}
