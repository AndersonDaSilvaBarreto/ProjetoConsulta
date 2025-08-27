package br.com.smartmed.consultas.model;

import br.com.smartmed.consultas.rest.dto.FormaPagamentoDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "forma_pagamento")
public class FormaPagamentoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "descricao",length = 64,nullable = false)
    @NotNull(message = "A descrição da forma de pagamento não pode ser nula.")
    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    /*
    public FormaPagamentoDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, FormaPagamentoDTO.class);
    }

     */
}
