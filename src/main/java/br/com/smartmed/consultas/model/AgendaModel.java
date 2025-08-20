package br.com.smartmed.consultas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "agenda")
public class AgendaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicoID", nullable = false)
    private MedicoModel medico;

    @Column(name = "data",nullable = false)
    private LocalDate data;

    @OneToMany(mappedBy = "agenda", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BlocoHorarioModel> blocos = new ArrayList<>();

    public void adicionarBloco(BlocoHorarioModel bloco) {
        blocos.add(bloco);
        bloco.setAgenda(this);
    }

    public void removerBloco(BlocoHorarioModel bloco) {
        blocos.remove(bloco);
        bloco.setAgenda(null);
    }

}
