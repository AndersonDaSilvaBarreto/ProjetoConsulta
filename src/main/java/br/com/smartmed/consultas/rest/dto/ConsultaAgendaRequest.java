package br.com.smartmed.consultas.rest.dto;

public class ConsultaAgendaRequest {
    private Integer medicoID;
    private String data;

    public Integer getMedicoID() {
        return medicoID;
    }

    public void setMedicoID(Integer medicoID) {
        this.medicoID = medicoID;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
