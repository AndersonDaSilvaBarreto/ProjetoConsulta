package br.com.smartmed.consultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "consulta")
public class ConsultaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dataHoraConsulta", nullable = false)
    private LocalDateTime dataHoraConsulta;

    @Column(name = "status",length = 16,nullable = false)
    @NotBlank(message = "O status é obrigatório!")
    private String status;

    @Column(name = "valor",nullable = false)
    @NotNull(message = "O valor não poder ser nulo")
    private BigDecimal valor;

    @Column(name = "observacoes",length = 1024,nullable = true)
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pacienteID", nullable = false)
    private PacienteModel paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicoID", nullable = false)
    private MedicoModel medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formaPagamentoID", nullable = true)
    private FormaPagamentoModel formaPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "convenioID", nullable = true)
    private ConvenioModel convenio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recepcionistaID", nullable = true)
    private RecepcionistaModel recepcionista;

    /*
    public ConsultaDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ConsultaDTO.class);
    }

     */


}
