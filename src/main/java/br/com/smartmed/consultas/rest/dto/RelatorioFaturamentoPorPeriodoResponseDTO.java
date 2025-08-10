package br.com.smartmed.consultas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioFaturamentoPorPeriodoResponseDTO {
private BigDecimal totalGeral;
private List<FormaPagamentoFaturamentoDTO> porFormaPagamento;
private List<ConvenioFaturamentoDTO> porConvenio;
}
