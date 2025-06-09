package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.exception.*;
import br.com.smartmed.consultas.model.EspecialidadeModel;
import br.com.smartmed.consultas.repository.EspecialidadeRepository;
import br.com.smartmed.consultas.rest.dto.EspecialidadeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EspecialidadeService {
    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Transactional(readOnly = true)
    public EspecialidadeDTO obterPorId(int id) {
        EspecialidadeModel especialidade = especialidadeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("\"Especialidade com id \" + id + \"não encontrado!\")"));
        return especialidade.toDTO();
    }

    @Transactional(readOnly = true)
    public List<EspecialidadeDTO> obterTodos() {
        List<EspecialidadeModel> especialidades = especialidadeRepository.findAll();
        return especialidades.stream().map(especialidade -> especialidade.toDTO()).collect(Collectors.toList());
    }

    @Transactional
    public EspecialidadeDTO salvar(EspecialidadeModel novaEspecialidade) {
        try {
            if(especialidadeRepository.existsById(novaEspecialidade.getId())) {
                throw new ConstraintException("Já existe uma especialidade com esse id " + novaEspecialidade.getId() + " na base de dados!");
            }
            return especialidadeRepository.save(novaEspecialidade).toDTO();
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar a especialidade " + novaEspecialidade.getNome() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar a especialidade " + novaEspecialidade.getNome() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar a especialidade " + novaEspecialidade.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar a especialidade " + novaEspecialidade.getNome() + ". Falha na conexão com o banco de dados!");
        }
    }

    @Transactional
    public EspecialidadeDTO atualizar(EspecialidadeModel especialidadeExistente) {
        try {
            if(!especialidadeRepository.existsById(especialidadeExistente.getId())) {
                throw new ConstraintException("A especialidade com esse id " + especialidadeExistente.getId() + " não existe na base de dados!");
            }
            return especialidadeRepository.save(especialidadeExistente).toDTO();
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar a especialidade " + especialidadeExistente.getNome() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar a especialidade " + especialidadeExistente.getNome() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar a especialidade " + especialidadeExistente.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar a especialidade " + especialidadeExistente.getNome() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível atualizar a especialidade " + especialidadeExistente.getNome() + ". Não encontrado no banco de dados!");
        }
    }

    @Transactional
    public void deletar(EspecialidadeModel especialidadeExistente) {
        try {
            //Caso ocorra uma tentativa de deletar um paciente que não existe utilizando o id.
            if (!especialidadeRepository.existsById(especialidadeExistente.getId())) {
                throw new ConstraintException("Especialidade inexistente na base de dados!");
            }

            //Deletar a especialidade na base de dados.
            especialidadeRepository.delete(especialidadeExistente);

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar a especialidade " + especialidadeExistente.getNome() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar a especialidade " + especialidadeExistente.getNome() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar a especialidade " + especialidadeExistente.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o deletar " + especialidadeExistente.getNome() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível deletar a especialidade" + especialidadeExistente.getNome() + ". Não encontrado no banco de dados!");
        }
    }
}
