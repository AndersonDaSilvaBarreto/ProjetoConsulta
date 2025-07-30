package br.com.smartmed.consultas.rest.dto;

import java.util.List;

public class ConsultaAgendaResponse {
    private String medico;
    private String data;
    private List<String> horariosOcupados;
    private List<String> horariosDisponiveis;

    public ConsultaAgendaResponse(String medico, String data, List<String> horariosOcupados, List<String> horariosDisponiveis) {
        this.medico = medico;
        this.data = data;
        this.horariosOcupados = horariosOcupados;
        this.horariosDisponiveis = horariosDisponiveis;
    }

    public String getMedico() {
        return medico;
    }

    public String getData() {
        return data;
    }

    public List<String> getHorariosOcupados() {
        return horariosOcupados;
    }

    public List<String> getHorariosDisponiveis() {
        return horariosDisponiveis;
    }
}
