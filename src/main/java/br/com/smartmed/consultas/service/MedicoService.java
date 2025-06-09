package br.com.smartmed.consultas.service;


import br.com.smartmed.consultas.exception.*;
import br.com.smartmed.consultas.model.MedicoModel;
import br.com.smartmed.consultas.repository.MedicoRepository;
import br.com.smartmed.consultas.rest.dto.MedicoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional(readOnly = true)
    public MedicoDTO obterPorId(int id) {
        MedicoModel medico = medicoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Médico com id " + id + "não encontrado!"));
        return medico.toDTO();
    }

    @Transactional(readOnly = true)
    public List<MedicoDTO> obterTodos() {
        List<MedicoModel> medicos = medicoRepository.findAll();
        return medicos.stream().map(medico -> medico.toDTO()).collect(Collectors.toList());

    }
    @Transactional
    public MedicoDTO salvar(MedicoModel novoMedico) {

        try {
            if(medicoRepository.existsByCrm(novoMedico.getCrm())) {
                throw new ConstraintException("Já existe um médico com esse Crm" + novoMedico.getCrm() + "na base de dados");
            }
            return medicoRepository.save(novoMedico).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar o médico " + novoMedico.getNome() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar o médico " + novoMedico.getNome() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar o médico " + novoMedico.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar o médico " + novoMedico.getNome() + ". Falha na conexão com o banco de dados!");
        }
    }

    @Transactional
    public MedicoDTO atualizar(MedicoModel medicoExistente) {
       try {
           if(!medicoRepository.existsByCrm(medicoExistente.getCrm())) {
               throw new ConstraintException("O médico com esse Crm " + medicoExistente.getCrm() + "não existe na base dados!");
           }
           return medicoRepository.save(medicoExistente).toDTO();
       } catch (DataIntegrityException e) {
           throw new DataIntegrityException("Erro! Não foi possível atualizar o médico " + medicoExistente.getNome() + " !");
       } catch (ConstraintException e) {
           // Relança a mensagem original ou adiciona contexto
           if (e.getMessage() == null || e.getMessage().isBlank()) {
               throw new ConstraintException("Erro ao atualizar o médico " + medicoExistente.getNome() + ": Restrição de integridade de dados.");
           }
           throw e;
       } catch (BusinessRuleException e) {
           throw new BusinessRuleException("Erro! Não foi possível atualizar o médico " + medicoExistente.getNome() + ". Violação de regra de negócio!");
       } catch (SQLException e) {
           throw new SQLException("Erro! Não foi possível atualizar o médico " + medicoExistente.getNome() + ". Falha na conexão com o banco de dados!");
       } catch (ObjectNotFoundException e) {
           throw new ObjectNotFoundException("Erro! Não foi possível atualizar o médico " + medicoExistente.getNome() + ". Não encontrado no banco de dados!");
       }
    }

    @Transactional
    public void deletar(MedicoModel medicoExistente) {
        try {
            if(!medicoRepository.existsByCrm(medicoExistente.getCrm())) {
                throw new ConstraintException("Médico enexistente na base de dados!");
            }
            medicoRepository.delete(medicoExistente);
        }  catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar o paciente " + medicoExistente.getNome() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar o paciente " + medicoExistente.getNome() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar o paciente " + medicoExistente.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o deletar " + medicoExistente.getNome() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível deletar o paciente" + medicoExistente.getNome() + ". Não encontrado no banco de dados!");
        }
    }
}
