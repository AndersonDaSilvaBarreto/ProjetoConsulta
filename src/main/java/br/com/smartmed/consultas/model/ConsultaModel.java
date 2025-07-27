package br.com.smartmed.consultas.model;

import br.com.smartmed.consultas.rest.dto.ConsultaDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

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
    private long id;

    @Column(name = "dataHoraConsulta", nullable = false)
    private LocalDateTime dataHoraConsulta;

    @Column(name = "status",length = 16,nullable = false)
    @NotNull(message = "O status não pode ser nulo.")
    @NotBlank(message = "O status é obrigatório!")
    private String status;

    @Column(name = "valor",nullable = false)
    @NotNull(message = "O valor não poder ser nulo")
    private float valor;

    @Column(name = "observacoes",length = 1024,nullable = true)
    private String observacoes;

    @Column(name = "pacienteID",nullable = false)
    @NotNull(message = "O identificador único de paciente não pode ser nulo!")
    private int pacienteID;

    @Column(name = "medicoID", nullable = false)
    @NotNull(message = "O identificador de médico não pode ser nulo!")
    private int medicoID;

    @Column(name = "formaPagamentoID", nullable = true)
    @NotNull(message = "O identificador único da forma de pagamento não pode ser nulo!")
    private int formaPagamentoID;

    @Column(name = "convenioID", nullable = true)
    @NotNull(message = "O identificador único do convencio não pode ser nulo!")
    private int convenioID;

    @Column(name = "recepcionistaID", nullable = false)
    @NotNull(message = "O identificador único de recepcionista não pode ser nulo!")
    private int recepcionistaID;

    /*
    public ConsultaDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ConsultaDTO.class);
    }

     */


}
