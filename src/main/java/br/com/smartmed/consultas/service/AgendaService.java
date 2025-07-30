package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.model.MedicoModel;
import br.com.smartmed.consultas.rest.dto.ConsultaAgendaResponse;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AgendaService {

    public List<String> gerarHorariosPadrao() {
        List<String> horarios = new ArrayList<>();
        LocalTime inicio = LocalTime.of(8, 0);
        LocalTime fim = LocalTime.of(12, 0);
        while (!inicio.isAfter(fim.minusMinutes(30))) {
            horarios.add(inicio.format(DateTimeFormatter.ofPattern("HH:mm")));
            inicio = inicio.plusMinutes(30);
        }
        return horarios;
    }

    public ConsultaAgendaResponse consultarAgenda(MedicoModel medico, String data) {
        List<String> todosHorarios = gerarHorariosPadrao();
        List<String> ocupados = medico.getAgenda().getOrDefault(data, new ArrayList<>());

        List<String> disponiveis = todosHorarios.stream()
            .filter(h -> {
                LocalDate hoje = LocalDate.now();
                LocalTime agora = LocalTime.now();
                if (data.equals(hoje.toString())) {
                    return !ocupados.contains(h) && LocalTime.parse(h).isAfter(agora);
                }
                return !ocupados.contains(h);
            })
            .collect(Collectors.toList());

        return new ConsultaAgendaResponse(medico.getNome(), data, ocupados, disponiveis);
    }
}
