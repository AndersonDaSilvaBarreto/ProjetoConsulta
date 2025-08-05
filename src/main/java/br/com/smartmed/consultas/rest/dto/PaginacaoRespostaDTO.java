package br.com.smartmed.consultas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginacaoRespostaDTO<T> {
    private List<T> conteudo;
    private int totalPaginas;
    private int paginaAtual;
}
