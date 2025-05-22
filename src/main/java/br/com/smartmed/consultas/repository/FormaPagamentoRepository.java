package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.FormaPagamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamentoModel, Integer> {

}
