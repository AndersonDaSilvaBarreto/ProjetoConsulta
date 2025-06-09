package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.exception.*;
import br.com.smartmed.consultas.model.FormaPagamentoModel;
import br.com.smartmed.consultas.repository.FormaPagamentoRepository;
import br.com.smartmed.consultas.rest.dto.FormaPagamentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormaPagamentoService {
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Transactional(readOnly = true)
    public FormaPagamentoDTO obterPorId(int id) {
        FormaPagamentoModel formaPagamento = formaPagamentoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Forma de pagamento com id " + id + "não encontrado!"));
        return formaPagamento.toDTO();
    }

    @Transactional(readOnly = true)
    public List<FormaPagamentoDTO> obterTodos() {
        List<FormaPagamentoModel> formaPagamentos = formaPagamentoRepository.findAll();
        return formaPagamentos.stream().map(formaPagamento -> formaPagamento.toDTO()).collect(Collectors.toList());
    }

    @Transactional
    public FormaPagamentoDTO salvar(FormaPagamentoModel novaFormaPagamento) {

        try {
            if(formaPagamentoRepository.existsById(novaFormaPagamento.getId())) {
                throw new ConstraintException("Já existe uma forma de pagamento com esse Id " + novaFormaPagamento.getId() + "na base de dados" );
            }
            return formaPagamentoRepository.save(novaFormaPagamento).toDTO();
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar a forma de pagamento " + novaFormaPagamento.getId() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar a forma de pagamento " + novaFormaPagamento.getId() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar a forma de pagamento " + novaFormaPagamento.getId() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar a forma de pagamento " + novaFormaPagamento.getId() + ". Falha na conexão com o banco de dados!");
        }

    }

    @Transactional
    public FormaPagamentoDTO atualizar(FormaPagamentoModel formaPagamentoExistente) {

        try {
            if (!formaPagamentoRepository.existsById(formaPagamentoExistente.getId())) {
                throw new ConstraintException("O recepcionista com esse CPF " + formaPagamentoExistente.getId() + " não existe na base de dados!");
            }
            return formaPagamentoRepository.save(formaPagamentoExistente).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar a forma de pagamento " + formaPagamentoExistente.getId() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar a forma de pagamento " + formaPagamentoExistente.getId() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar a forma de pagamento " + formaPagamentoExistente.getId() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar a forma de pagamento " + formaPagamentoExistente.getId() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível atualizar a forma de pagamento " + formaPagamentoExistente.getId() + ". Não encontrado no banco de dados!");
        }
    }

    public void deletar(FormaPagamentoModel formaPagamentoExistente) {
        try {
            if(!formaPagamentoRepository.existsById(formaPagamentoExistente.getId())) {
                throw new ConstraintException("Forma de pagamento inexistente na base de dados!");
            }
            formaPagamentoRepository.delete(formaPagamentoExistente);
        }  catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar a forma de pagamento " + formaPagamentoExistente.getId() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar a forma de pagamento " + formaPagamentoExistente.getId() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar a forma de pagamento " + formaPagamentoExistente.getId() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o deletar " + formaPagamentoExistente.getId() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível deletar a forma de pagamento " + formaPagamentoExistente.getId() + ". Não encontrado no banco de dados!");
        }
    }
}
