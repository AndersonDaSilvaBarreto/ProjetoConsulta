package br.com.smartmed.consultas.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medico")
public class MedicoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "nome",length = 255,nullable = false)
    @NotNull(message = "O nome não pode ser nulo.")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Column(name = "crm",length = 8, nullable = false, unique = true)
    @Length(min = 8, max = 8, message = "O crm tem exatamente 8 números" )
    @NotNull(message = "O crm não pode ser nulo.")
    @NotBlank(message = "O crm é obrigatório")
    private String crm;

    @Column(name = "telefone",length = 11,nullable = false)
    @Length(min = 11, max = 11, message = "O campo deve ter exatamente 11 números. Ex: 7991130366")
    @NotNull(message = "O telefone não pode ser nulo.")
    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;

    @Column(name = "email", length = 64, nullable = true)
    @Email(message = "E-mail invalido")
    private String email;

    @Column(name = "valorConsultaReferencia",nullable = false)
    private BigDecimal valorConsultaReferencia;

    @Column(name = "ativo",nullable = false)
    private boolean ativo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidadeID", nullable = false)
    private EspecialidadeModel especialidade;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AgendaModel> agendas = new ArrayList<>();



    /*
    @Transient
    private Map<String, List<String>> agenda = new HashMap<>();

    public boolean marcarConsulta(LocalDate data, LocalTime horaInicio, int duracaoMinutos) {
        // Converter data para string no formato yyyy-MM-dd para usar como chave
        String dataStr = data.toString();
        List<String> horariosOcupados = agenda.getOrDefault(dataStr, new ArrayList<>());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime horaFim = horaInicio.plusMinutes(duracaoMinutos);

        // Verificar conflitos de horários já ocupados
        for (String h : horariosOcupados) {
            LocalTime ocupado = LocalTime.parse(h, formatter);
            if (!ocupado.isBefore(horaInicio) && ocupado.isBefore(horaFim)) {
                return false; // conflito encontrado
            }
        }

        // Adicionar blocos de 10 minutos na agenda
        LocalTime temp = horaInicio;
        while (!temp.isAfter(horaFim.minusMinutes(1))) {
            horariosOcupados.add(temp.format(formatter));
            temp = temp.plusMinutes(10);
        }

        agenda.put(dataStr, horariosOcupados);
        return true;
    }



    public MedicoDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, MedicoDTO.class);
    }

     */
}
