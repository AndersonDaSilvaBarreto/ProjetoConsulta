package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.exception.*;
import br.com.smartmed.consultas.model.ConsultaModel;
import br.com.smartmed.consultas.repository.ConsultaRepository;
import br.com.smartmed.consultas.rest.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public ConsultaDTO obterPorId(Long id) {
        ConsultaModel consulta = consultaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Consulta com ID " + id + " não encontrado."));
        return modelMapper.map(consulta, ConsultaDTO.class);
    }

    @Transactional(readOnly = true)
    public List<ConsultaDTO> obterTodos() {
        List<ConsultaModel> consultas = consultaRepository.findAll();
        return consultas.stream().map(consulta -> modelMapper.map(consulta,ConsultaDTO.class)).collect(Collectors.toList());
    }

    @Transactional

    public ConsultaDTO salvar(ConsultaModel novaConsulta) {
        try {
            if(consultaRepository.existsById(novaConsulta.getId())) {
                throw new ConstraintException("Já existe uma consulta com esse ID " + novaConsulta.getId() + " na base de dados!");
            }
            return modelMapper.map(consultaRepository.save(novaConsulta), ConsultaDTO.class);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar a consulta " + novaConsulta.getId() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar a consulta " + novaConsulta.getId() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar a consulta " + novaConsulta.getId() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar a consulta " + novaConsulta.getId() + ". Falha na conexão com o banco de dados!");
        }
    }

    @Transactional
    public ConsultaDTO atualizar(ConsultaModel consultaExistente) {
        try {
            if(!consultaRepository.existsById(consultaExistente.getId())) {
                throw new ConstraintException("A consulta com esse ID " + consultaExistente.getId() + " não existe na base de dados!");
            }
            return modelMapper.map(consultaRepository.save(consultaExistente), ConsultaDTO.class);
        }  catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar a consulta " + consultaExistente.getId() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar a consulta " + consultaExistente.getId() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar a consulta " + consultaExistente.getId() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar a consulta " + consultaExistente.getId() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível atualizar a consulta " + consultaExistente.getId() + ". Não encontrado no banco de dados!");
        }
    }

    @Transactional
    public void deletar(ConsultaModel consultaExistente) {
        try {
            if(!consultaRepository.existsById(consultaExistente.getId())) {
                throw new ConstraintException("Consulta inexistente na base de dados!");
            }
            consultaRepository.delete(consultaExistente);

        }  catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar a consulta " + consultaExistente.getId() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar a consulta " + consultaExistente.getId() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar a consulta " + consultaExistente.getId() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar a deletar " + consultaExistente.getId() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível deletar a consulta" + consultaExistente.getId() + ". Não encontrado no banco de dados!");
        }
    }

    @Transactional(readOnly = true)
    public List<ConsultaHistoricoDTO> buscarHistorico(ConsultaHistoricoInputDTO filtro) {
        // Ele já retorna o DTO pronto, por isso não tá usando o map
        return consultaRepository.buscarHistoricoComFiltros(filtro);
    }

    @Transactional
    public ConsultaCancelamentoResponse cancelar(ConsultaCancelamentoRequest request) {

        if (request.getMotivo() == null || request.getMotivo().isBlank()) {
            throw new IllegalArgumentException("O motivo do cancelamento é obrigatório.");
        }

        ConsultaModel consulta = consultaRepository.findById(request.getConsultaID())
                .orElseThrow(() -> new RuntimeException("Consulta com ID " + request.getConsultaID() + " não encontrada."));

        if (!"AGENDADA".equalsIgnoreCase(consulta.getStatus())) {
            throw new RuntimeException("Apenas consultas com status 'AGENDADA' podem ser canceladas.");
        }

        if (consulta.getDataHoraConsulta().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Não é possível cancelar uma consulta que já ocorreu.");
        }

        consultaRepository.cancelamentoConsulta(consulta.getId(), request.getMotivo());

        return new ConsultaCancelamentoResponse("Consulta cancelada com sucesso", "CANCELADA");
    }


}


