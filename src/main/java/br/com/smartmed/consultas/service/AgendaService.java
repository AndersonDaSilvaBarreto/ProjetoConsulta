package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.model.AgendaModel;
import br.com.smartmed.consultas.model.BlocoHorarioModel;
import br.com.smartmed.consultas.model.MedicoModel;
import br.com.smartmed.consultas.repository.AgendaRepository;
import br.com.smartmed.consultas.repository.BlocoHorarioRepository;
import br.com.smartmed.consultas.rest.dto.ConsultaAgendaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AgendaService {

    @Autowired
    AgendaRepository agendaRepository;

    @Autowired
    BlocoHorarioRepository blocoHorarioRepository;


    @Transactional
    public boolean marcarConsulta(MedicoModel medico, LocalDate data, LocalTime horaInicio, int duracaoMinutos) {
        // Tenta buscar agenda já existente
        AgendaModel agenda = agendaRepository.findByMedicoAndData(medico, data).orElse(null);

        // Se não existir, cria se for segunda a sexta
        if (agenda == null) {
            DayOfWeek diaSemana = data.getDayOfWeek();
            if (diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY) {
                return false; // Não cria agenda no fim de semana
            }

            agenda = new AgendaModel();
            agenda.setMedico(medico);
            agenda.setData(data);
            agenda.setBlocos(new ArrayList<>());
            agendaRepository.save(agenda);
        }

        LocalTime horaFim = horaInicio.plusMinutes(duracaoMinutos);

        // Gerar blocos de 10 minutos da nova consulta
        List<LocalTime> novosBlocos = new ArrayList<>();
        LocalTime temp = horaInicio;
        while (!temp.isAfter(horaFim.minusMinutes(1))) {
            novosBlocos.add(temp);
            temp = temp.plusMinutes(10);
        }

        // Verificar conflitos com blocos existentes
        for (BlocoHorarioModel bloco : agenda.getBlocos()) {
            LocalTime blocoInicio = bloco.getHoraInicio();
            LocalTime blocoFim = bloco.getHoraFim();

            for (LocalTime novo : novosBlocos) {
                boolean conflito = !novo.isBefore(blocoInicio) && novo.isBefore(blocoFim);
                if (conflito) {
                    return false; // conflito encontrado
                }
            }
        }

        // Criar blocos de 10 minutos e adicionar à agenda
        for (LocalTime novo : novosBlocos) {
            BlocoHorarioModel bloco = new BlocoHorarioModel();
            bloco.setHoraInicio(novo);
            bloco.setHoraFim(novo.plusMinutes(10));
            bloco.setAgenda(agenda);
            agenda.getBlocos().add(bloco);
        }

        // Salvar agenda com os novos blocos
        agendaRepository.save(agenda);

        return true;
    }
    public List<String> gerarHorariosPadrao() {
        List<String> horarios = new ArrayList<>();
        LocalTime inicio = LocalTime.of(8, 0);   // início do expediente
        LocalTime fim = LocalTime.of(18, 0);     // fim do expediente

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        while (!inicio.isAfter(fim.minusMinutes(10))) {
            horarios.add(inicio.format(formatter));
            inicio = inicio.plusMinutes(10);
        }

        return horarios;
    }


    public ConsultaAgendaResponse consultarAgenda(MedicoModel medico, LocalDate data) {
        List<String> todosHorarios = gerarHorariosPadrao();

        // Busca a agenda do médico para a data
        AgendaModel agenda = agendaRepository.findByMedicoAndData(medico, data)
                .orElse(null);

        // Obtém os horários ocupados a partir dos blocos existentes
        List<String> ocupados = new ArrayList<>();
        if (agenda != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            for (BlocoHorarioModel bloco : agenda.getBlocos()) {
                LocalTime temp = bloco.getHoraInicio();
                LocalTime fimBloco = bloco.getHoraFim();
                while (!temp.isAfter(fimBloco.minusMinutes(1))) {
                    ocupados.add(temp.format(formatter));
                    temp = temp.plusMinutes(10); // mantém blocos de 10 min
                }
            }
        }

        // Filtra os horários disponíveis
        List<String> disponiveis = todosHorarios.stream()
                .filter(h -> {
                    LocalDate hoje = LocalDate.now();
                    LocalTime agora = LocalTime.now();
                    if (data.equals(hoje)) {
                        return !ocupados.contains(h) && LocalTime.parse(h, DateTimeFormatter.ofPattern("HH:mm")).isAfter(agora);
                    }
                    return !ocupados.contains(h);
                })
                .collect(Collectors.toList());

        return new ConsultaAgendaResponse(medico.getNome(), data.toString(), ocupados, disponiveis);
    }
}
